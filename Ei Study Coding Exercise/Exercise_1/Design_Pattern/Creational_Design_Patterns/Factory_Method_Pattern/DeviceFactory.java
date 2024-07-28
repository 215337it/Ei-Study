public abstract class DeviceFactory {
    public abstract SmartDevice createDevice(String type);

    public SmartDevice getDevice(String type) {
        SmartDevice device = createDevice(type);
        // Additional configuration can be done here
        return device;
    }
}

class ConcreteDeviceFactory extends DeviceFactory {
    @Override
    public SmartDevice createDevice(String type) {
        switch (type.toLowerCase()) {
            case "thermostat":
                return new Thermostat(1, "Living Room Thermostat", false);
            case "light":
                return new Light(2, "Bedroom Light", false);
            // Add more device types as needed
            default:
                throw new IllegalArgumentException("Unknown device type");
        }
    }
}
