package com.dc.controller;


import com.dc.dto.PageResponseDTO;
import com.dc.dto.PatientCreateRequestDTO;
import com.dc.dto.PatientResponseDTO;
import com.dc.entity.UserAuthEntity;
import com.dc.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;


    @PostMapping()
    public ResponseEntity<Map<String,String>> createPatient(@RequestBody @Valid PatientCreateRequestDTO patientCreateRequestDTO, @AuthenticationPrincipal
                                                UserAuthEntity userAuthEntity){
        Map<String,String> map = new HashMap<>();
        String patientCode = patientService.createPatient(patientCreateRequestDTO,userAuthEntity);
        map.put("patientCode",patientCode);
        map.put("message", String.format("Patient Created Successfully with ID : %s",patientCode));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/lovs/{type}")
    public ResponseEntity<List<Map<String,String>>> getPatientLovs(@PathVariable("type") String type){
        return new ResponseEntity<>(patientService.getPatientLovs(type), HttpStatus.OK);
    }

    @GetMapping("/{patientCode}")
    public ResponseEntity<PatientResponseDTO> getPatientDetails(@PathVariable("patientCode") String patientCode){
        return new ResponseEntity<>(patientService.getPatientDetails(patientCode), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<PatientResponseDTO>> getPatients(
            @RequestParam(name = "email", required = false, defaultValue = "") String email,
            @RequestParam(name = "visitDate", required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date visitDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @AuthenticationPrincipal UserAuthEntity userAuthEntity){


        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("DESC") ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return new ResponseEntity<>(patientService.getPatients(email,visitDate,userAuthEntity,pageRequest), HttpStatus.OK);
    }
}
