package com.dc.controller;


import com.dc.dto.PatientCreateRequestDTO;
import com.dc.entity.UserAuthEntity;
import com.dc.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;
    private final Validator validator;


    @PostMapping()
    public ResponseEntity<String> createPatient(@RequestBody @Valid PatientCreateRequestDTO patientCreateRequestDTO, @AuthenticationPrincipal
                                                UserAuthEntity userAuthEntity){
        return new ResponseEntity<>(patientService.createPatient(patientCreateRequestDTO,userAuthEntity), HttpStatus.CREATED);
    }
}
