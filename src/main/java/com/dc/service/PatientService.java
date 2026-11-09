package com.dc.service;

import com.dc.dto.PageResponseDTO;
import com.dc.dto.PatientCreateRequestDTO;
import com.dc.dto.PatientResponseDTO;
import com.dc.entity.UserAuthEntity;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PatientService {
    String createPatient(PatientCreateRequestDTO patientCreateRequestDTO, UserAuthEntity userAuthEntity);
    List<Map<String,String>> getPatientLovs(String type);
    PatientResponseDTO getPatientDetails(String patientCode);
    PageResponseDTO<PatientResponseDTO> getPatients(String email, Date visitDate,UserAuthEntity userAuthEntity,Pageable pageable);
}
