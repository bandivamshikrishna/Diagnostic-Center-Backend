package com.dc.mapper;

import com.dc.dto.PatientCreateRequestDTO;
import com.dc.entity.PatientEntity;

import java.util.Date;

public class PatientMapper {

    public static PatientEntity fromCreateDTOToEntity(PatientCreateRequestDTO patientCreateRequestDTO){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName(patientCreateRequestDTO.getPatientName());
        patientEntity.setEmail(patientCreateRequestDTO.getEmail());
        patientEntity.setPhoneNumber(patientCreateRequestDTO.getPhoneNumber());
        patientEntity.setDateOfBirth(patientCreateRequestDTO.getDob());
        patientEntity.setSampleCollectedDate(patientCreateRequestDTO.getSampleCollectedDate());
        patientEntity.setSampleCollectedTime(patientCreateRequestDTO.getSampleCollectedTime());
        patientEntity.setReportedDate(patientCreateRequestDTO.getReportedDate());
        patientEntity.setReportedTime(patientCreateRequestDTO.getReportedTime());
        return patientEntity;
    }
}
