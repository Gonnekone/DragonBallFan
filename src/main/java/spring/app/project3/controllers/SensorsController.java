package spring.app.project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.app.project3.dto.SensorDTO;
import spring.app.project3.exceptions.SensorNotCreatedException;
import spring.app.project3.models.Sensor;
import spring.app.project3.services.SensorsService;
import spring.app.project3.util.SensorDTOValidator;
import spring.app.project3.util.SensorErrorResponse;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private SensorsService sensorsService;
    private ModelMapper modelMapper;
    private SensorDTOValidator sensorDTOValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorDTOValidator sensorDTOValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorDTOValidator = sensorDTOValidator;
    }

    @PostMapping("/registration")
    public void register(@RequestBody @Valid SensorDTO sensorDTO,
                         BindingResult bindingResult) {

        sensorDTOValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMessage.toString());
        }


        sensorsService.save(convertToSensor(sensorDTO));
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(e.getMessage());

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
