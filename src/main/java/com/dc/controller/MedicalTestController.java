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


    @GetMapping("/lovs/departments")
    public ResponseEntity<List<Map<String,String>>> getMedicalTestDepartments(){
        return new ResponseEntity<>(medicalTestService.getMedicalTestDepartments(),HttpStatus.OK);
    }

    @GetMapping("/lovs/categories")
    public ResponseEntity<List<Map<String,String>>> getMedicalTestCategories(){
        return new ResponseEntity<>(medicalTestService.getMedicalTestCategories(),HttpStatus.OK);
    }

    @GetMapping("/lovs/methods")
    public ResponseEntity<List<Map<String,String>>> getMedicalTestMethods(){
        return new ResponseEntity<>(medicalTestService.getMedicalTestMethods(),HttpStatus.OK);
    }

    @GetMapping("/lovs/specimens")
    public ResponseEntity<List<Map<String,String>>> getMedicalTestSpecimens(){
        return new ResponseEntity<>(medicalTestService.getMedicalTestSpecimens(),HttpStatus.OK);
    }

    @GetMapping("/lovs/units")
    public ResponseEntity<List<Map<String,String>>> getMedicalTestUnits(){
        return new ResponseEntity<>(medicalTestService.getMedicalTestUnits(),HttpStatus.OK);
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

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateMedicalTestByID(@PathVariable(name = "id") Long id, @Valid @RequestBody MedicalTestUpdateRequestDTO medicalTestUpdateRequestDTO){
        return new ResponseEntity<>(medicalTestService.updateMedicalTestById(id,medicalTestUpdateRequestDTO),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<MedicalTestResponseDTO>> getAllMedicalTests(
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
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @AuthenticationPrincipal UserAuthEntity userAuthEntity){

        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("DESC") ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return new ResponseEntity<>(medicalTestService.getAllMedicalTests(userAuthEntity,testCode,testName,category,department,startDate,endDate, filterType,pageRequest), HttpStatus.OK);
    }
}
