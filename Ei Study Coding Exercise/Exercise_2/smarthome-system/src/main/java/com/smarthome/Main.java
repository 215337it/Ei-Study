package com.smarthome;

import com.smarthome.scheduler.TaskScheduler;

public class Main {
    public static void main(String[] args) {
        DeviceManager deviceManager = DeviceManager.getInstance();
        TaskScheduler scheduler = new TaskScheduler(deviceManager);
        CommandLineInterface cli = new CommandLineInterface(deviceManager, scheduler);
        cli.run();
    }
}
