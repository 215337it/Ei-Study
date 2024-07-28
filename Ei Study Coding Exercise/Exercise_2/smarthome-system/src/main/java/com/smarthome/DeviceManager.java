package com.smarthome;

import com.smarthome.devices.SmartDevice;
import com.smarthome.logging.Logger;
import com.smarthome.scheduler.ScheduledTask;
import com.smarthome.scheduler.TaskScheduler;

import com.smarthome.exceptions.DeviceNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceManager {
    private static DeviceManager instance;
    private List<SmartDevice> devices;
    private List<ScheduledTask> scheduledTasks;
    private Timer timer;
     private TaskScheduler scheduler;

    public TaskScheduler getScheduler() {
        return scheduler;
    }
    
    
    public void setScheduler(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }
    private DeviceManager() {
        devices = new ArrayList<>();
        this.scheduledTasks = new ArrayList<>();
        this.timer = new Timer(true);
         startScheduler();
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
    // public void printDevices() {
    // for (SmartDevice device : devices) {
    // System.out.println(device);
    // }
    // }

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

    public void scheduleTask(int deviceId, LocalDateTime time, String command) {
        scheduledTasks.add(new ScheduledTask(deviceId, time, command));
        Logger.log("Scheduled task: " + command + " for device ID: " + deviceId + " at " + time);
    }

    private void startScheduler() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                List<ScheduledTask> executedTasks = new ArrayList<>();
                for (ScheduledTask task : scheduledTasks) {
                    if (!task.getTime().isAfter(now)) {
                        try {
                            executeTask(task);
                            executedTasks.add(task);
                        } catch (DeviceNotFoundException e) {
                            Logger.logError(e.getMessage());
                        }
                    }
                }
                scheduledTasks.removeAll(executedTasks);
            }
        }, 0, 1000); // Check every second
    }

    private void executeTask(ScheduledTask task) throws DeviceNotFoundException {
        SmartDevice device = getDevice(task.getDeviceId());
        switch (task.getCommand().toLowerCase()) {
            case "turnon":
                device.turnOn();
                break;
            case "turnoff":
                device.turnOff();
                break;
            default:
                Logger.logError("Unknown command: " + task.getCommand());
        }
    }
}
