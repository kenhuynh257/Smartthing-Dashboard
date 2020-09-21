package com.smartthing.SmartthingKeypad.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SHMDevices {
    @Getter
    private final String[] deviceID = {"fa3a4770-7dad-4110-a7e7-d58caadb9550","e4c6c14d-c4ad-4a0a-aece-fd60e0f59c33"};
    private final String disarmID = "0ec972d8-287b-4188-92ac-44da1fd396ad";

    public boolean isSHMDevices(@NotNull String id) {
        if (Arrays.stream(deviceID).anyMatch(id::equals)) {
            return true;
        }
        return false;
    }

    public boolean isDisarmButton(String id) {
        if (id.equals(disarmID)) {
            return true;
        }
        return false;
    }

}
