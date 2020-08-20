package com.smartthing.SmartthingKeypad.rest_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/passsword")
public class CheckPassword {
    @GetMapping(path = "/")
    public @ResponseBody
    ResponseEntity<String> authorize(String password, String deviceID, String Capabilites, String status){

    }
}
