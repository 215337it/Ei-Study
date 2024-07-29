package com.smarthome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Thermostat;
import com.smarthome.exceptions.DeviceNotFoundException;
import com.smarthome.logging.Logger;
import com.smarthome.scheduler.TaskScheduler;
import com.smarthome.scheduler.Trigger;

public class CommandLineInterface {

    private DeviceManager manager;
    private Scanner scanner;
    private final TaskScheduler scheduler;

    public CommandLineInterface(DeviceManager manager, TaskScheduler scheduler) {
        this.manager = manager;
        this.scheduler = scheduler;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        Logger.log("Smart Home System started.");
        showMenuAndHandleChoice();
    }

    private void showMenuAndHandleChoice() {
        try {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine().trim());

            if (choice == 7) {
                Logger.log("Exiting Smart Home System.");
                System.exit(0); // End the recursion
            } else {
                handleUserChoice(choice);
                showMenuAndHandleChoice(); // Recursive call to show the menu again
            }

        } catch (NumberFormatException e) {
            Logger.logError("Invalid input format: " + e.getMessage());
            System.out.println("Please enter a valid number.");
            showMenuAndHandleChoice(); // Recursive call to prompt the user again
        } catch (Exception e) {
            Logger.logError("An unexpected error occurred: " + e.getMessage());
            System.out.println("An error occurred. Please try again.");
            showMenuAndHandleChoice(); // Recursive call to prompt the user again
        }
    }

   
    private void showMenu() {
        System.out.println("\n1. Add Device");
        System.out.println("2. Remove Device");
        System.out.println("3. List Devices");
        System.out.println("4. Control Device");
        System.out.println("5. Schedule Task");
        System.out.println("6. Add Trigger");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                addDevice();
                break;
            case 2:
                removeDevice();
                break;
            case 3:
                manager.listDevices();
                break;
            case 4:
                controlDevice();
                break;
            case 5:
                scheduleTask();
                break;
            case 6:
                addTrigger();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    private void addDevice() {
        System.out.print("Enter device type (Light/Thermostat/DoorLock): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter device name: ");
        String name = scanner.nextLine().trim();

        SmartDevice device = DeviceFactory.createDevice(type, name);
        if (device != null) {
            manager.addDevice(device);
            //manager.printDevices();
        } else {
            Logger.logError("Invalid device type: " + type);
            System.out.println("Invalid device type.");
        }
    }

    private void removeDevice() {
        try {
            System.out.print("Enter device ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            manager.removeDevice(id);
            //manager.printDevices();
        } catch (NumberFormatException e) {
            Logger.logError("Invalid ID format: " + e.getMessage());
            System.out.println("Invalid ID format.");
        } catch (DeviceNotFoundException e) {
            Logger.logError("Device not found: " + e.getMessage());
            System.out.println("Device not found.");
        }
    }

    private void controlDevice() {
        try {
            System.out.print("Enter device ID to control: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            SmartDevice device = manager.getDevice(id);
            //manager.printDevices();
            if (device != null) {
                showControlMenu(device);
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        device.turnOn();
                        break;
                    case 2:
                        device.turnOff();
                        break;
                    case 3:
                        device.status();
                        break;
                    //if(device instanceof Thermostat){
                    case 4:
                        if (device instanceof Thermostat) {
                            System.out.print("Enter temperature: ");
                            int temp = Integer.parseInt(scanner.nextLine().trim());
                            ((Thermostat) device).setTemperature(temp);
                        }
                        break;
                    //}
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                throw new DeviceNotFoundException("Device with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            Logger.logError("Invalid ID format: " + e.getMessage());
            System.out.println("Invalid ID format.");
        } catch (DeviceNotFoundException e) {
            Logger.logError(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private void scheduleTask() {
        try {
            System.out.print("Enter device ID to schedule: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            SmartDevice device = manager.getDevice(id);
            if (device != null) {
                System.out.print("Enter command (turnOn/turnOff): ");
                String command = scanner.nextLine().trim();
                System.out.print("Enter time (yyyy-MM-dd HH:mm): ");
                String timeString = scanner.nextLine().trim();
                LocalDateTime time = LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                scheduler.scheduleTask(id, time, command);
                System.out.println("Task scheduled.");
            } else {
                throw new DeviceNotFoundException("Device with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            Logger.logError("Invalid ID format: " + e.getMessage());
            System.out.println("Invalid ID format.");
        } catch (DeviceNotFoundException e) {
            Logger.logError(e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            Logger.logError("Error scheduling task: " + e.getMessage());
            System.out.println("Error scheduling task. Ensure the date and time format is correct.");
        }
    }

    private void showControlMenu(SmartDevice device) {
        System.out.println("1. Turn On");
        System.out.println("2. Turn Off");
        System.out.println("3. Status");
        if (device instanceof Thermostat) {
            System.out.println("4. Set Temperature");
        }
        System.out.print("Enter your choice: ");
    }

    private void addTrigger() {
        try {
            System.out.print("Enter condition (e.g., temperature > 75 / doorlock = open/close ): ");
            String condition = scanner.nextLine().trim();
            System.out.print("Enter action (e.g., turnoff/turnon): ");
            String action = scanner.nextLine().trim();
            System.out.print("Enter device id to perform action: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            scheduler.addTrigger(new Trigger(condition, action, id));
            System.out.println("Trigger added.");

        } catch (Exception e) {
            Logger.logError("Error adding trigger: " + e.getMessage());
            System.out.println("Error adding trigger.");
        }
    }
}
