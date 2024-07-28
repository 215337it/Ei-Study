package com.smarthome;

import com.smarthome.devices.SmartDevice;
import com.smarthome.logging.Logger;

import com.smarthome.scheduler.TaskScheduler;

import com.smarthome.exceptions.DeviceNotFoundException;


import java.util.ArrayList;
import java.util.List;


public class DeviceManager {
    private static DeviceManager instance;
    private List<SmartDevice> devices;
  
     private TaskScheduler scheduler;

    public TaskScheduler getScheduler() {
        return scheduler;
    }
    
    
    public void setScheduler(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }
    private DeviceManager() {
        devices = new ArrayList<>();
        //this.scheduledTasks = new ArrayList<>();
        //this.timer = new Timer(true);
       // startScheduler();
    }

    public static synchronized DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    public List<SmartDevice> getDevices() {
        return devices;
    }

    public void addDevice(SmartDevice device) {
        devices.add(device);
        Logger.log("Device added: " + device.getName());
    }

    public void removeDevice(int deviceId) throws DeviceNotFoundException {
        SmartDevice device = getDevice(deviceId);
        if (device != null) {
            devices.remove(device);
            Logger.log("Device removed: " + deviceId);
        } else {
            throw new DeviceNotFoundException("Device with ID " + deviceId + " not found.");
        }
    }
    

    public SmartDevice getDevice(int deviceId) {
        return devices.stream()
                .filter(device -> device.getId() == deviceId)
                .findFirst()
                .orElse(null);
    }

    public void listDevices() {
        devices.forEach(device -> System.out.println(device));
    }

    public void notifyAllDevices(String message) {
        devices.forEach(device -> device.update(message));
    }

    
}
