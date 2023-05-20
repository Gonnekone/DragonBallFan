package spring.app.project3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.app.project3.dto.SensorDTO;
import spring.app.project3.repositories.SensorsRepository;
import spring.app.project3.services.SensorsService;

@Component
public class SensorDTOValidator implements Validator {

    private SensorsRepository sensorsRepository;

    @Autowired
    public SensorDTOValidator(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        if (sensorsRepository.findByName(sensorDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with that name is already exist");
        }
    }
}
