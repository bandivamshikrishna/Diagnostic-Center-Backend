package com.dc.controller;


import com.dc.dto.PatientCreateRequestDTO;
import com.dc.enums.PatientStatusEnum;
import com.dc.service.PatientService;
import com.dc.validators.DraftValidation;
import com.dc.validators.FinalValidation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;
    private final Validator validator;


    @PostMapping()
    public ResponseEntity<String> createPatient(@RequestBody @Validated PatientCreateRequestDTO patientCreateRequestDTO){
        Class<?> group = patientCreateRequestDTO.getStatus().toString().equalsIgnoreCase(PatientStatusEnum.FINAL.toString())
                ? FinalValidation.class : DraftValidation.class;
        Set<ConstraintViolation<PatientCreateRequestDTO>> violations = validator.validate(patientCreateRequestDTO,group);
        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);
        return new ResponseEntity<>(patientService.createPatient(patientCreateRequestDTO), HttpStatus.CREATED);
    }
}
