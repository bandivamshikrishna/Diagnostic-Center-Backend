package com.dc.service;

import com.dc.dto.PatientCreateRequestDTO;
import com.dc.entity.UserAuthEntity;

public interface PatientService {
    public String createPatient(PatientCreateRequestDTO patientCreateRequestDTO, UserAuthEntity userAuthEntity);
}
