package com.smarthome;

import com.smarthome.devices.Light;
import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Thermostat;
import com.smarthome.devices.DoorLock;

public class DeviceFactory {
    private static int idCounter = 1;

    public static SmartDevice createDevice(String type, String name) {
        switch (type.toLowerCase()) {
            case "light":
                return new Light(idCounter++, name,false);
            case "thermostat":
                return new Thermostat(idCounter++, name,false);
            case "doorlock":
                return new DoorLock(idCounter++, name,false);
            default:
                return null;
        }
    }
}
