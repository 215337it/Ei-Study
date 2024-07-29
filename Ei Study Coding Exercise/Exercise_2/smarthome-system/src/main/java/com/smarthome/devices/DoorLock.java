package com.smarthome.devices;

import com.smarthome.DeviceManager;
import com.smarthome.scheduler.TaskScheduler;

public class DoorLock extends SmartDevice {
    private boolean isLocked;
    

    public DoorLock(int id, String name, boolean status) {
        super(id, name, status ? "locked" : "unlocked", "doorlock");    
        this.isLocked = status;
       
    }
    public boolean isLocked() {
        return isLocked;
    }
    
    @Override
    public void turnOn() {

        isLocked = true;
        setStatus("locked");
        
        System.out.println(getName() + " is now LOCKED.");
        TaskScheduler.getInstance(DeviceManager.getInstance()).evaluateAllTriggers();
    }

    @Override
    public void turnOff() {
        isLocked = false;
        setStatus("unlocked");
        
        System.out.println(getName() + " is now UNLOCKED.");
        TaskScheduler.getInstance(DeviceManager.getInstance()).evaluateAllTriggers();
    }

    @Override
    public void status() {
        System.out.println(getName() + " is currently " + (isLocked ? "LOCKED" : "UNLOCKED"));
    }

    @Override
    public void update(String message) {
        System.out.println(getName() + " received update: " + message);
    }
}
