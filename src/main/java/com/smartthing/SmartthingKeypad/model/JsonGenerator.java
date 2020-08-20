package com.smartthing.SmartthingKeypad.model;

import com.smartthing.SmartthingKeypad.data.Capability;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;


/*
Generate json
 */
public class JsonGenerator {
    private final OkHttpClient client;

    public JsonGenerator() {
        client = new OkHttpClient();
    }

    private static boolean containsCapabilityEnum(String text) {
        for (Capability c : Capability.values()) {
            if (c.name().equals(text.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private String lookUpTable(String text) {
        switch (Capability.valueOf(text.toUpperCase())) {
            case WATERSENSOR:
                return "water";
            case CONTACTSENSOR:
                return "contact";
            default:
                return text.toLowerCase();
        }
    }

    public String constructJson(String input) throws IOException {
        JSONArray json = new JSONArray();

        JSONObject strInput = new JSONObject(input);
        JSONArray item = strInput.getJSONArray("items");


        for (int i = 0; i < item.length(); i++) {
            //new trim json for each device
            JSONObject trimDevice = new JSONObject();

            JSONObject device = item.getJSONObject(i);
            //device id
            String deviceID = device.getString("deviceId");
            //get capabilities array

            //System.out.println(componentArray.toString());
            JSONArray componentArray = device.getJSONArray("components");
            //System.out.println(capabilityArray.getJSONObject(1).toString());

            for (int z = 0; z < componentArray.length(); z++) {
                JSONObject capabilityObj = componentArray.getJSONObject(z);
                System.out.println(capabilityObj);

                JSONArray capabilityArray = capabilityObj.getJSONArray("capabilities");
                //status capability Array
                JSONArray statusCapabilityArray = new JSONArray();

                for (int j = 0; j < capabilityArray.length(); j++) {
                    JSONObject capability = capabilityArray.getJSONObject(j);

                    String capabilityName = capability.getString("id");
                    //System.out.println(capabilityName.toUpperCase());
                    if (containsCapabilityEnum(capabilityName)) {
                        System.out.println("capacityName: " + capabilityName);
                        JSONObject statusCapability = getCapabilityStatus(deviceID, capabilityName);
                        statusCapabilityArray.put(statusCapability);
                    }
                }


                //add capabilities array to json
                trimDevice.put("capabilities", statusCapabilityArray);
                //device id
                //String deviceID = device.getString("deviceId");
                trimDevice.put("deviceId", deviceID);

                //add label name
                trimDevice.put("label", device.getString("label"));
                //System.out.println("TrimDevice: " + trimDevice);

                //add room
                try {
                    trimDevice.put("room", getRoom(device.getString("roomId"), device.getString("locationId")));
                } catch (Exception e) {
                    trimDevice.put("room", "NoAssignRoom");
                }

            }

            //end json
            json.put(trimDevice);

        }
        System.out.println("FinalJson: " + json.toString(4));

        return json.toString();

    }

    private JSONObject getCapabilityStatus(String deviceID, String capabilityID) throws IOException {
        String url = "https://api.smartthings.com/v1/devices/"
                + deviceID
                + "/components/main/capabilities/"
                + capabilityID
                + "/status";

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", RemoteKey.key)
                .build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject res = new JSONObject(Objects.requireNonNull(response.body()).string());

            JSONObject statusCapability = new JSONObject();
            //System.out.println(res);
            statusCapability.put(capabilityID, res.getJSONObject(lookUpTable(capabilityID)).getString("value"));

            return statusCapability;
        }

    }

    private String getRoom(String roomID, String locationID) throws IOException {
        if (Room.room.containsKey(roomID)) {
            return Room.room.get(roomID);
        }
        String url = "https://api.smartthings.com/v1/locations/" +
                locationID +
                "/rooms/" +
                roomID;
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", RemoteKey.key)
                .build();
        try (Response response = client.newCall(request).execute()) {
            JSONObject res = new JSONObject(Objects.requireNonNull(response.body()).string());
            String roomName = res.getString("name");
            Room.room.put(roomID, roomName);
            return roomName;
        }

    }

}
