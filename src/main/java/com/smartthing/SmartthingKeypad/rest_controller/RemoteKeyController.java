package com.smartthing.SmartthingKeypad.rest_controller;


import com.smartthing.SmartthingKeypad.model.RemoteKey;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for key input
 */
@RestController
@RequestMapping(path = "/RemoteKey")
public class RemoteKeyController {
    /**
     * Get the remote key
     *
     * @return remote key
     */
    @GetMapping(path = "/")
    public String getRemoteKey() {
        return RemoteKey.key;
    }

    /**
     * @param key: input key
     * @return: that key
     */
    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public String setRemoteKey(@RequestBody String key) {
        RemoteKey.key = "Bearer " + key.replaceAll(".$", "");
        System.out.println(RemoteKey.key);
        return RemoteKey.key;
    }
}
