package com.dc.service;

import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MedicalTestService {

    List<Map<String,String>> getMedicalTestDepartments();
    List<Map<String,String>> getMedicalTestCategories();
    List<Map<String,String>> getMedicalTestMethods();
    List<Map<String,String>> getMedicalTestSpecimens();
    List<Map<String,String>> getMedicalTestUnits();
    String createMedicalTest(MedicalTestCreateRequestDTO medicalTestCreateRequestDTO, UserAuthEntity userAuthEntity);
    MedicalTestResponseDTO getMedicalTestByID(Long id);
    String updateMedicalTestById(Long id, MedicalTestUpdateRequestDTO medicalTestUpdateRequestDTO);
    PageResponseDTO<MedicalTestResponseDTO> getAllMedicalTests(UserAuthEntity userAuthEntity, String testCode, String testName, String category,
                                                     String department, Date startDate, Date endDate, String filterType, Pageable pageable);
}
