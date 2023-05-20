package spring.app.project3.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @Min(value = -100, message = "Value should be greater or equals than -100")
    @Max(value = 100, message = "Value should be lower or equals than 100")
    @NotNull(message = "Value shouldn't be empty")
    private Double value;

    @NotNull(message = "Raining meaning shouldn't be empty")
    private Boolean raining;

    @NotNull(message = "Sensor should be")
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensorDTO) {
        this.sensor = sensorDTO;
    }
}
