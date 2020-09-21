package com.smartthing.SmartthingKeypad.rest_controller;

import com.smartthing.SmartthingKeypad.model.PasswordCode;
import com.smartthing.SmartthingKeypad.model.RemoteKey;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/SendCommnad")
public class SendCommand {


    @PostMapping(path = "/Disarm")
    @ResponseBody
    public ResponseEntity<String> authorize(@RequestBody String[] infoArray) throws IOException, NullPointerException {
        String status = infoArray[3];
        if (status == "on") {
            return new ResponseEntity<>("Cant do that", HttpStatus.NOT_ACCEPTABLE);
        }

        String password = infoArray[0];
        String capability = infoArray[2];
        String deviceID = infoArray[1];
        if (password.equals(PasswordCode.password)) {
            return sendCommand(deviceID,capability,status);

        }

        return new ResponseEntity<>("Wrong Password", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/Arm")
    @ResponseBody
    public ResponseEntity<String> armSystem(@RequestBody String[] infoArray) throws IOException, NullPointerException {
        String status = infoArray[2];
        if (status == "on") {
            return new ResponseEntity<>("Cant do that", HttpStatus.NOT_ACCEPTABLE);
        }

        String deviceID = infoArray[0];
        String capability= infoArray[1];
        return sendCommand(deviceID,capability,status);

    }

    private ResponseEntity<String> sendCommand(String deviceID, String capability, String status) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject JSONBody = commandBody(capability, "on");

        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSONBody.toString(), mediaType);

        String url = "https://api.smartthings.com/v1/devices/" + deviceID + "/commands";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", RemoteKey.key)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            System.out.println("Command Return: " + res);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    private JSONObject commandBody(String capability, String status) {
        JSONObject res = new JSONObject();
        JSONArray body = new JSONArray();
        JSONObject temp = new JSONObject();

        temp.put("component", "main");
        temp.put("capability", capability);
        temp.put("command", status);
        temp.put("arguments", new JSONArray());

        body.put(temp);

        res.put("commands", body);
        System.out.println("command body: " + res.toString());
        return res;
    }
}
