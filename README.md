# Smart Home System

## Overview

The Smart Home System is a console-based application designed to manage various smart devices in a home. The application supports functionalities such as adding/removing devices, controlling devices, scheduling tasks, and more. This project demonstrates the use of different software design patterns to manage the system effectively.

## Features

- Add and remove devices
- Control devices (turn on/off)
- Schedule tasks for devices
- Monitor device status
- Log system events
- Exception handling

## Design Patterns Demonstrated

1. **Behavioral Design Patterns**
   - Observer Pattern: Update all devices when a change occurs in the system.
   - Command Pattern: Encapsulate requests as objects to control devices.

2. **Creational Design Patterns**
   - Factory Method Pattern: Create instances of different smart devices.
   - Singleton Pattern: Ensure a single instance of the device manager.

3. **Structural Design Patterns**
   - Proxy Pattern: Control access to the devices.
   - Adapter Pattern: Adapt an interface for compatibility between different devices.

## Prerequisites

- Java Development Kit (JDK) 8 or later

## Installation

### Step 1: Install JDK

1. Download JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.java.net/).
2. Follow the installation instructions for your operating system.
3. Set the `JAVA_HOME` environment variable to point to the JDK installation directory.

### Step 2: Install Apache Maven (optional)

1. Download Maven from the [Maven website](https://maven.apache.org/download.cgi).
2. Follow the installation instructions for your operating system.
3. Add the `bin` directory of the extracted directory to the `PATH` environment variable.

## Project Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/smart-home-system.git
    cd smart-home-system
    ```

2. Build the project using Maven (only required for building, not for running pre-built JAR):

    ```bash
    mvn clean install
    ```

    Alternatively, download the pre-built JAR file from the Releases section of the repository.

## Running the Application

1. Navigate to the directory containing the pre-built JAR file (if you downloaded it):

    ```bash
    cd path/to/jar-file
    ```

2. Run the application using the following command:

    ```bash
    java -jar smart-home-system.jar
    ```

## Project Directory Structure

```
smart-home-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── smarthome/
│   │   │   │   │   ├── Main.java
│   │   │   │   │   ├── CommandLineInterface.java
│   │   │   │   │   ├── DeviceFactory.java
│   │   │   │   │   ├── DeviceManager.java
│   │   │   │   │   ├── devices/
│   │   │   │   │   │   ├── SmartDevice.java
│   │   │   │   │   │   ├── Light.java
│   │   │   │   │   │   ├── Thermostat.java
│   │   │   │   │   │   ├── DoorLock.java
│   │   │   │   │   ├── logging/
│   │   │   │   │   │   ├── Logger.java
│   │   │   │   │   ├── exceptions/
│   │   │   │   │   │   ├── DeviceNotFoundException.java
│   │   │   │   │   ├── scheduler/
│   │   │   │   │   │   ├── TaskScheduler.java
│   │   │   │   │   │   ├── Trigger.java
│   │   │   │   │   │   ├── ScheduledTask.java
├── pom.xml
├── README.md
```

## Usage

### Available Commands

- `add_device [Device Type] [Device Name]` - Adds a new device.
- `remove_device [Device Name]` - Removes a device.
- `turn_on [Device Name]` - Turns on a device.
- `turn_off [Device Name]` - Turns off a device.
- `schedule_task [Device Name] [Task] [Time]` - Schedules a task for a device.
- `list_devices` - Lists all devices.
- `get_device_status [Device Name]` - Gets the status of a device.
- `help` - Displays the help message.
- `exit` - Exits the application.

### Examples

Adding a device:
```bash
Enter command: add_device Thermostat LivingRoomThermostat
```

Removing a device:
```bash
Enter command: remove_device LivingRoomThermostat
```

Turning on a device:
```bash
Enter command: turn_on LivingRoomThermostat
```

Scheduling a task:
```bash
Enter command: schedule_task LivingRoomThermostat "Heat to 72F" 2024-08-01T10:00
```

Listing devices:
```bash
Enter command: list_devices
```

Displaying help:
```bash
Enter command: help
```

Exiting the application:
```bash
Enter command: exit
```

## Additional Features

- `search_device [Device Name]` - Searches for a device by name.

---

Feel free to customize further based on specific implementation details or additional features in your project.
