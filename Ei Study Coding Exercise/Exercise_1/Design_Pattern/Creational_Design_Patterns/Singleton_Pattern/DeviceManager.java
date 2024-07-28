public class DeviceManager {
    private static DeviceManager instance;
    private List<SmartDevice> devices;

    private DeviceManager() {
        devices = new ArrayList<>();
    }

    public static synchronized DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    public void addDevice(SmartDevice device) {
        devices.add(device);
    }

    // Other methods...
}
