public interface TemperatureSensor {
    double getTemperature();
}

public class CelsiusThermostat {
    public double getTemperatureInCelsius() {
        return 22.0;
    }
}

public class TemperatureAdapter implements TemperatureSensor {
    private CelsiusThermostat celsiusThermostat;

    public TemperatureAdapter(CelsiusThermostat celsiusThermostat) {
        this.celsiusThermostat = celsiusThermostat;
    }

    @Override
    public double getTemperature() {
        return celsiusThermostat.getTemperatureInCelsius();
    }
}
