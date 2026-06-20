package com.dc.service;

import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MedicalTestService {

    List<Map<String,String>> getMedicalTestLovs(String type);
    String createMedicalTest(MedicalTestCreateRequestDTO medicalTestCreateRequestDTO, UserAuthEntity userAuthEntity);
    MedicalTestResponseDTO getMedicalTestByID(Long id);
    String updateMedicalTestById(Long id, MedicalTestCreateRequestDTO medicalTestUpdateRequestDTO,UserAuthEntity updatedBy);
    PageResponseDTO<MedicalTestListResponseDTO> getAllMedicalTests(UserAuthEntity userAuthEntity, String testCode, String testName, String category,
                                                     String department, Date startDate, Date endDate, String filterType, Pageable pageable);
    String activateOrDeActivateMedicalTest(Long id);
}
