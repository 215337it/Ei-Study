package com.smarthome.devices;

public class Light extends SmartDevice {
    private boolean isOn;
     

    public Light(int id, String name,boolean status) {
        super(id, name,check(status));
        this.isOn = status;
        
    }
    static String check(boolean isOn){
        return (isOn)?"on":"off";
    }
    @Override
    public void turnOn() {
        isOn = true;
        
        setStatus("on");
        
        System.out.println(getName() + " is now ON.");
    }

    @Override
    public void turnOff() {
        isOn = false;
        
        setStatus("off");
      
        System.out.println(getName() + " is now OFF.");
    }

    @Override
    public void status() {
        System.out.println(getName() + " is currently " + check(isOn));
    }

    @Override
    public void update(String message) {
        System.out.println(getName() + " received update: " + message);
    }
}
