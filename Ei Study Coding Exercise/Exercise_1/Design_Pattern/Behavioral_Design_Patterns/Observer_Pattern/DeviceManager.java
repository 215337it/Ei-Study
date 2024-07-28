import java.util.ArrayList;
import java.util.List;

public class DeviceManager {
    private List<SmartDevice> devices = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public void addDevice(SmartDevice device) {
        devices.add(device);
        notifyAllObservers();
    }

    public void removeDevice(SmartDevice device) {
        devices.remove(device);
        notifyAllObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

interface Observer {
    void update();
}

class Thermostat implements Observer {
    private String name;

    public Thermostat(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println(name + " received update.");
    }
}
