package com.smartthing.SmartthingKeypad.rest_controller;


import com.smartthing.SmartthingKeypad.data.RemoteKey;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/RemoteKey")
public class RemoteKeyController {

    @GetMapping(path = "/")
    public String getRemoteKey() {
        return RemoteKey.key;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public String setRemoteKey(@RequestBody String key) {
        RemoteKey.key = "Bearer " + key.replaceAll(".$", "");
        System.out.println(RemoteKey.key);
        return RemoteKey.key;
    }
}
