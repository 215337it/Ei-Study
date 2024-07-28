package com.smarthome.scheduler;

import com.smarthome.devices.DoorLock;
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
    boolean excuted=false;

    public TaskScheduler(DeviceManager deviceManager) {
        this.deviceManager = deviceManager;
        instance = this;
        startScheduler();
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
             
                scheduledTasks.removeAll(executedTasks);
               
            }
        }, 0, 1000); // Check every second
    }

    public void evaluateAllTriggers() {
        List<Trigger> executedTriggers = new ArrayList<>();
        for (Trigger trigger : triggers) {
            evaluateTrigger(trigger);
            if(excuted){
                executedTriggers.add(trigger);
                excuted=false;
            }
            
        }
        triggers.removeAll(executedTriggers);
    }

    private void evaluateTrigger(Trigger trigger) {
        String[] parts = trigger.getCondition().split(" ");
        if (parts.length == 3) {
            String operator = parts[1];
           

            for (SmartDevice device : deviceManager.getDevices()) {
                if (device instanceof Thermostat) {
                    int value = Integer.parseInt(parts[2]);
                    int currentTemp = ((Thermostat) device).getTemperature();
                    //System.out.println("Current temperature: " + currentTemp);
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
                            executeAction(trigger.getAction(),trigger.getId());
                        } catch (DeviceNotFoundException e) {
                            Logger.logError(e.getMessage());
                        }
                    }
                }
                else if(device instanceof DoorLock){
                    boolean isLocked=((DoorLock) device).isLocked();
                   
                    boolean currentcondition;
                    if(parts[2].equals("open")){
                         currentcondition=false;
                    }
                    else if(parts[2].equals("close")){
                         currentcondition=true;
                    }
                    
                        else {
                            throw new IllegalArgumentException("Invalid condition: " + parts[2]);
                        }
                    
                   
                    if(isLocked==currentcondition){
                        try {
                            executeAction(trigger.getAction(),trigger.getId());
                        } catch (DeviceNotFoundException e) {
                            Logger.logError(e.getMessage());
                        }
                    }
                    
                }
            }
       }
    }

    private void executeAction(String action,int id) throws DeviceNotFoundException {
        String command=action.toLowerCase();
        
        if(command.equals("turnon")||command.equals("turnoff")){
            
            executeTask(id,command);
        }
        else{
            Logger.logError("Unknown action: " + action);
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
                excuted=true;
                break;
            case "turnoff":
                System.out.println("\n" + "Notification");
                device.turnOff();
                excuted=true;
                break;
            default:
                Logger.logError("Unknown command: " + command);
        }
    }

    
}
