package com.smartthing.SmartthingKeypad.model;

public class SwitchStatus {
    private final String[]  statusArr = {"on", "off"};
    public SwitchStatus(){

    }

    public String changeStatus(String status){
        if(status.equals(statusArr[0])){
            return statusArr[1];
        }
        return statusArr[0];
    }
}
