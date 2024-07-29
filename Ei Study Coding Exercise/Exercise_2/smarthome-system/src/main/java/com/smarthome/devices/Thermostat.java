package com.smarthome.devices;

import com.smarthome.DeviceManager;
import com.smarthome.scheduler.TaskScheduler;


public class Thermostat extends SmartDevice {
    public int temperature = 70;
    


    public Thermostat(int id, String name, boolean status) {
        super(id, name, check(status), "thermostat");
    }
    
    static String check(boolean isOn) {
        return (isOn) ? "on" : "off";
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println(getName() + " is now set to " + temperature + " degrees.");
        
        TaskScheduler.getInstance(DeviceManager.getInstance()).evaluateAllTriggers();
    }
    

    public int getTemperature() {
        return temperature;
    }

    @Override
    public void turnOn() {
        setStatus(check(true));
        System.out.println(getName() + " thermostat is now ON.");
    }

    @Override
    public void turnOff() {
        setStatus(check(false));
        System.out.println(getName() + " thermostat is now OFF.");
    }

    @Override
    public void status() {
        if (getStatus().equals("on")) {
            System.out.println(getName() + " thermostat is currently ON.");
            System.out.println(getName() + " is currently set to " + temperature + " degrees.");
        } else {
            System.out.println(getName() + " thermostat is currently OFF.");
        }
    }

    @Override
    public void update(String message) {
        System.out.println(getName() + " received update: " + message);
    }
}

