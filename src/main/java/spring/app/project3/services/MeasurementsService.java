package spring.app.project3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.project3.models.Measurement;
import spring.app.project3.models.Sensor;
import spring.app.project3.repositories.MeasurementsRepository;
import spring.app.project3.repositories.SensorsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private MeasurementsRepository measurementsRepository;
    private SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void save(Measurement measurement) {
        Sensor sensor = measurement.getSensor();
        measurement.setSensor(sensorsRepository.findByName(sensor.getName()).get());
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public long getRainyDaysCount() {
        return measurementsRepository.findAll().stream().filter(Measurement::isRaining).count();
    }
}
