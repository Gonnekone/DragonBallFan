package spring.app.project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.app.project3.dto.MeasurementDTO;
import spring.app.project3.exceptions.MeasurementNotCreatedException;
import spring.app.project3.models.Measurement;
import spring.app.project3.services.MeasurementsService;
import spring.app.project3.util.MeasuremantErrorResponse;
import spring.app.project3.util.MeasurementDTOValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private MeasurementsService measurementsService;
    private ModelMapper modelMapper;
    private MeasurementDTOValidator measurementDTOValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementDTOValidator measurementDTOValidator) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementDTOValidator = measurementDTOValidator;
    }

    @PostMapping("/add")
    public void add(@RequestBody @Valid MeasurementDTO measurementDTO,
                    BindingResult bindingResult) {

        measurementDTOValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorMessage.toString());
        }

        measurementsService.save(convertToMeasurement(measurementDTO));
    }

    @GetMapping
    public List<MeasurementDTO> showAll() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public long showRainyDaysCount() {
        return measurementsService.getRainyDaysCount();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasuremantErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasuremantErrorResponse measuremantErrorResponse = new MeasuremantErrorResponse(e.getMessage());
        measuremantErrorResponse.setTime(LocalDateTime.now());

        return new ResponseEntity<>(measuremantErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
