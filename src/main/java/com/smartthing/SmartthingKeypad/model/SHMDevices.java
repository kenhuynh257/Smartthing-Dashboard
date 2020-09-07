package com.smartthing.SmartthingKeypad.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SHMDevices {
    @Getter
    private final String[] deviceID = {"04c5d925-ab57-47e6-bd28-77fede884636"};
    private final String disarmID = "04c5d925-ab57-47e6-bd28-77fede884636";

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
