package com.smarthome.devices;

public class DoorLock extends SmartDevice {
    private boolean isLocked;
    

    public DoorLock(int id, String name, boolean status) {
        super(id, name, status ? "locked" : "unlocked");    
        this.isLocked = status;
       
    }
    
    @Override
    public void turnOn() {

        isLocked = true;
        setStatus("locked");
        
        System.out.println(getName() + " is now LOCKED.");
    }

    @Override
    public void turnOff() {
        isLocked = false;
        setStatus("unlocked");
        
        System.out.println(getName() + " is now UNLOCKED.");
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
