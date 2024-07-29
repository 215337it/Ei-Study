package com.smarthome.devices;

public abstract class SmartDevice {
    private int id;
    private String name;
    private String status;
    private String type;
    

    public SmartDevice(int id, String name, String status, String type) {
        this.id = id;
        this.name = name;
        this.status=status;
        this.type = type;
    }
    // public SmartDevice(int id, String name) {
    //     this(id, name, "off"); 
    // }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getStatus() {
        return status;
    }

    public abstract void turnOn();
    public abstract void turnOff();
    public abstract void status();
    
    public abstract void update(String message);

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name+'\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
