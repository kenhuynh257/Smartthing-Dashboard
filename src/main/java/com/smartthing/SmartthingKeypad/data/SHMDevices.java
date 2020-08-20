package com.smartthing.SmartthingKeypad.data;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SHMDevices {
    @Getter
    private static final String[] deviceID = {"04c5d925-ab57-47e6-bd28-77fede884636"};

    public static boolean isSHMDevices(@NotNull String id) {
        if (Arrays.stream(deviceID).anyMatch(id::equals)) {
            return true;
        }
        return false;
    }

}
