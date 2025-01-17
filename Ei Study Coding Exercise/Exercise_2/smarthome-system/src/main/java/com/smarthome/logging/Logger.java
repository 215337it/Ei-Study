package com.smarthome.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        System.out.println(LocalDateTime.now().format(formatter) + " INFO: " + message);
    }

    public static void logError(String message) {
        System.err.println(LocalDateTime.now().format(formatter) + " ERROR: " + message);
    }
}
