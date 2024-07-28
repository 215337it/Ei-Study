public class DeviceProxy implements SmartDevice {
    private RealDevice realDevice;
    private String deviceId;

    public DeviceProxy(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void turnOn() {
        if (realDevice == null) {
            realDevice = new RealDevice(deviceId);
        }
        realDevice.turnOn();
    }

    @Override
    public void turnOff() {
        if (realDevice == null) {
            realDevice = new RealDevice(deviceId);
        }
        realDevice.turnOff();
    }

    // Other methods...
}

class RealDevice implements SmartDevice {
    private String deviceId;

    public RealDevice(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void turnOn() {
        System.out.println("Device " + deviceId + " is now ON.");
    }

    @Override
    public void turnOff() {
        System.out.println("Device " + deviceId + " is now OFF.");
    }

    // Other methods...
}
