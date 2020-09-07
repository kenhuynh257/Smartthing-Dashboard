package com.smartthing.SmartthingKeypad.rest_controller;

import com.smartthing.SmartthingKeypad.model.PasswordCode;
import com.smartthing.SmartthingKeypad.model.RemoteKey;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/passsword")
public class CheckPassword {

    OkHttpClient client = new OkHttpClient();


    @GetMapping(path = "/")
    public @ResponseBody
    ResponseEntity<String> authorize(String password, String deviceID, String capability, String status) throws IOException, NullPointerException {
        if (password.equals(PasswordCode.password)) {

            JSONObject JSONBody = commandBody(capability, status);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(JSONBody.toString(), mediaType);

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
        return new ResponseEntity<>("Wrong Password", HttpStatus.NOT_ACCEPTABLE);
    }

    private JSONObject commandBody(String capability, String status) {
        JSONObject res = new JSONObject();
        JSONArray body = new JSONArray();
        JSONObject temp = new JSONObject();

        temp.put("component", "main");
        temp.put("capability", capability);
        temp.put("command", "on");
        temp.put("arguments", new JSONArray());

        body.put(temp);

        res.put("commands", body);
        System.out.println("command body: " + res.toString());
        return res;
    }
}
