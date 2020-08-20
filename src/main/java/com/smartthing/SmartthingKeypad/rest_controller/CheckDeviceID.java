package com.smartthing.SmartthingKeypad.rest_controller;

import com.smartthing.SmartthingKeypad.data.SHMDevices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/CheckDeviceID")
public class CheckDeviceID {

    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<String>
    isSHMDevice(@PathVariable String id){
        if(SHMDevices.isSHMDevices(id)){
            return new ResponseEntity<String>("ok", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Not support", HttpStatus.NOT_ACCEPTABLE);
    }
}
