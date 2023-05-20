package spring.app.project3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.app.project3.dto.MeasurementDTO;
import spring.app.project3.repositories.SensorsRepository;

@Component
public class MeasurementDTOValidator implements Validator {

    private SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementDTOValidator(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if (sensorsRepository.findByName(measurementDTO.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with that name wasn't found");
        }
    }
}
