package com.dc.controller;


import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import com.dc.service.MedicalTestService;
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
@RequestMapping("/api/medicaltest")
public class MedicalTestController {

    private final MedicalTestService medicalTestService;


    @GetMapping("/lovs")
    public ResponseEntity<List<Map<String,String>>> getMedicalTestLovs(
            @RequestParam(name = "type", required = true, defaultValue = "DEPARTMENT") String type){

        return new ResponseEntity<>(medicalTestService.getMedicalTestLovs(type),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Map<String,String>> createMedicalTest(@Valid @RequestBody MedicalTestCreateRequestDTO medicalTestCreateRequestDTO,
                                                                @AuthenticationPrincipal UserAuthEntity userAuthEntity){
        Map<String,String> map = new HashMap<>();
        map.put("message", medicalTestService.createMedicalTest(medicalTestCreateRequestDTO, userAuthEntity));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalTestResponseDTO> getMedicalTestByID(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(medicalTestService.getMedicalTestByID(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateMedicalTestByID(@PathVariable(name = "id") Long id, @Valid @RequestBody MedicalTestCreateRequestDTO medicalTestUpdateRequestDTO,
                                                        @AuthenticationPrincipal UserAuthEntity userAuthEntity){
        Map<String,String> map = new HashMap<>();
        map.put("message", medicalTestService.updateMedicalTestById(id,medicalTestUpdateRequestDTO,userAuthEntity));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<MedicalTestListResponseDTO>> getAllMedicalTests(
            @RequestParam(name = "filterType", required = false, defaultValue = "1") String filterType,
            @RequestParam(name = "ID", required = false, defaultValue = "") String testCode,
            @RequestParam(name = "testName",required = false, defaultValue = "") String testName,
            @RequestParam(name = "category", required = false, defaultValue = "") String category,
            @RequestParam(name = "department", required = false, defaultValue = "") String department,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @AuthenticationPrincipal UserAuthEntity userAuthEntity){

        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("DESC") ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return new ResponseEntity<>(medicalTestService.getAllMedicalTests(userAuthEntity,testCode,testName,category,department,startDate,endDate, filterType,pageRequest), HttpStatus.OK);
    }


    @PostMapping("/activateOrDeactivate/{id}")
    public ResponseEntity<Map<String,String>> activateOrDeactivateUser(@PathVariable("id") Long id){

        Map<String,String> map = new HashMap<>();
        map.put("message", medicalTestService.activateOrDeActivateMedicalTest(id));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @GetMapping("/manage-tests")
    public ResponseEntity<PageResponseDTO<ManageMedicalTestListResponseDTO>> manageMedicalTests(
            @RequestParam(name = "testName",required = false, defaultValue = "") String testName,
            @RequestParam(name = "category", required = false, defaultValue = "") String category,
            @RequestParam(name = "department", required = false, defaultValue = "") String department,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @AuthenticationPrincipal UserAuthEntity userAuthEntity){

        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("DESC") ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return new ResponseEntity<>(medicalTestService.manageMedicalTests(userAuthEntity,testName,category,department,pageRequest), HttpStatus.OK);
    }


    @PostMapping("/manage-tests")
    public ResponseEntity<Map<String,String>> updateMedicalTest(@RequestBody List<ManageMedicalTestCreateRequestDTO> tests,
                                                                @AuthenticationPrincipal UserAuthEntity userAuthEntity){
        Map<String,String> map = new HashMap<>();
        map.put("message", medicalTestService.updateMedicalTests(tests,userAuthEntity));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
