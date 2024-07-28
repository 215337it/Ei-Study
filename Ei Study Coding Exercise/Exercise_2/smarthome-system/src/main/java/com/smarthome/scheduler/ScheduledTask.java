package com.smarthome.scheduler;

import java.time.LocalDateTime;

public class ScheduledTask {
    private int deviceId;
    private LocalDateTime time;
    private String command;

    public ScheduledTask(int deviceId, LocalDateTime time, String command) {
        this.deviceId = deviceId;
        this.time = time;
        this.command = command;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getCommand() {
        return command;
    }
}
