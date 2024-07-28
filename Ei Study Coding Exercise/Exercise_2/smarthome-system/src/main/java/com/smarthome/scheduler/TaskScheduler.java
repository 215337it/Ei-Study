package com.smarthome.scheduler;

import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Thermostat;
import com.smarthome.DeviceManager;
import com.smarthome.logging.Logger;
import com.smarthome.exceptions.DeviceNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskScheduler {
    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();
    private List<Trigger> triggers = new ArrayList<>();
    private final Timer timer = new Timer();
    private final DeviceManager deviceManager;
    private static TaskScheduler instance;

    public TaskScheduler(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
        instance = this;
        //startScheduler();
    }
    

    
    public static synchronized TaskScheduler getInstance(DeviceManager deviceManager) {
        if (instance == null) {
            instance = new TaskScheduler(deviceManager);
        }
        return instance;
    }

    public void scheduleTask(int deviceId, LocalDateTime time, String command) {
        scheduledTasks.add(new ScheduledTask(deviceId, time, command));
        Logger.log("Task scheduled for device " + deviceId + " at " + time + " with command " + command);
    }

    public void addTrigger(Trigger trigger) {
        triggers.add(trigger);
        evaluateAllTriggers();
        //startScheduler();

    }

    public void startScheduler() {
        
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
                System.out.println("Starting scheduler...");
                scheduledTasks.removeAll(executedTasks);
                evaluateAllTriggers();

                // Stop the scheduler if there are no scheduled tasks or triggers
                if (scheduledTasks.isEmpty() && triggers.isEmpty()) {
                    Logger.log("No more scheduled tasks or triggers. Stopping scheduler.");
                    cancel();
                }
            }
        }, 0, 1000); // Check every second
    }

    public void evaluateAllTriggers() {
        List<Trigger> executedTriggers = new ArrayList<>();
        for (Trigger trigger : triggers) {
            evaluateTrigger(trigger);
            executedTriggers.add(trigger);
        }
        triggers.removeAll(executedTriggers);
    }

    private void evaluateTrigger(Trigger trigger) {
        String[] parts = trigger.getCondition().split(" ");
        if (parts.length == 3) {
            String operator = parts[1];
            int value = Integer.parseInt(parts[2]);

            for (SmartDevice device : deviceManager.getDevices()) {
                if (device instanceof Thermostat) {
                    int currentTemp = ((Thermostat) device).getTemperature();
                    System.out.println("Current temperature: " + currentTemp);
                    boolean conditionMet = false;

                    switch (operator) {
                        case ">":
                            conditionMet = currentTemp > value;
                            break;
                        case "<":
                            conditionMet = currentTemp < value;
                            break;
                        
                    }

                    if (conditionMet) {
                        try {
                            executeAction(trigger.getAction());
                        } catch (DeviceNotFoundException e) {
                            Logger.logError(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    private void executeAction(String action) throws DeviceNotFoundException {
        if (action.startsWith("turnOff(") && action.endsWith(")")) {
            int id = Integer.parseInt(action.substring(8, action.length() - 1));
            if (!isValidDeviceId(id)) {
                throw new DeviceNotFoundException("Device ID " + id + " not found.");
            }
            executeTask(id, "turnOff");
        } else if (action.startsWith("turnOn(") && action.endsWith(")")) {
            int id = Integer.parseInt(action.substring(7, action.length() - 1));
            if (!isValidDeviceId(id)) {
                throw new DeviceNotFoundException("Device ID " + id + " not found.");
            }
            executeTask(id, "turnOn");
        }
        
    }
    private void executeTask(ScheduledTask task) throws DeviceNotFoundException {
               
                SmartDevice device = deviceManager.getDevice(task.getDeviceId());
                if (device == null) {
                    throw new DeviceNotFoundException("Device with ID " + task.getDeviceId() + " not found.");
                }
                switch (task.getCommand().toLowerCase()) {
                    case "turnon":
                        System.out.println("\n"+"Notification");
                        device.turnOn();
                        
                        break;
                    case "turnoff":
                        System.out.println("\n"+"Notification");
                        device.turnOff();
                       
                        break;
                    default:
                        Logger.logError("Unknown command: " + task.getCommand());
                }
            }

    private void executeTask(int id, String command) throws DeviceNotFoundException {
        SmartDevice device = deviceManager.getDevice(id);
        if (device == null) {
            throw new DeviceNotFoundException("Device with ID " + id + " not found.");
        }
        switch (command.toLowerCase()) {
            case "turnon":
                System.out.println("\n" + "Notification");
                device.turnOn();
                break;
            case "turnoff":
                System.out.println("\n" + "Notification");
                device.turnOff();
                break;
            default:
                Logger.logError("Unknown command: " + command);
        }
    }

    private boolean isValidDeviceId(int id) {
        return true; 
    }
}
