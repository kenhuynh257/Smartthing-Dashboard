package com.smartthing.SmartthingKeypad.rest_controller;

import com.smartthing.SmartthingKeypad.model.RemoteKey;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/GetData")
public class SmartthingController {
    OkHttpClient client = new OkHttpClient();

    @GetMapping(path = "/")
    public String getData() throws IOException {

        Request request = new Request.Builder()
                .url("https://api.smartthings.com/v1/devices")
                .method("GET", null)
                .addHeader("Authorization", RemoteKey.key)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String resStr = response.body().string();
            System.out.println(resStr);
            return resStr;
        }


    }

}
