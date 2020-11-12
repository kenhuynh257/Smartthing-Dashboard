package com.smartthing.SmartthingKeypad.rest_controller;

import com.smartthing.SmartthingKeypad.model.SHMDevices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller check if the device need password to change status
 */
@RestController
@RequestMapping(path = "/CheckDeviceID")
public class CheckDeviceID {
    SHMDevices device = new SHMDevices();

    /**
     * Check if this is a SHM Device
     * @param id: id
     * @return: ok status with information body
     */
    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<String>
    isSHMDevice(@PathVariable String id) {
        if (device.isSHMDevices(id)) {
            return new ResponseEntity<String>("notDisarm", HttpStatus.OK);
        }
        if (device.isDisarmButton(id)) {
            return new ResponseEntity<>("disarm", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not support", HttpStatus.OK);
    }
}
