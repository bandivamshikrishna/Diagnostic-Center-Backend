package com.dc.mapper;

import com.dc.dto.PatientCreateRequestDTO;
import com.dc.dto.PatientResponseDTO;
import com.dc.entity.PatientEntity;

import java.util.Date;

public class PatientMapper {

    public static PatientEntity fromCreateDTOToEntity(PatientCreateRequestDTO patientCreateRequestDTO){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName(patientCreateRequestDTO.getName());
        patientEntity.setEmail(patientCreateRequestDTO.getEmail());
        patientEntity.setPhoneNumber(patientCreateRequestDTO.getPhoneNumber());
        patientEntity.setDateOfBirth(patientCreateRequestDTO.getDateOfBirth());
        return patientEntity;
    }

    public static PatientResponseDTO fromEntityToDTO(PatientEntity patientEntity){
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setTitle(patientEntity.getPatientTitle().getTitle());
        patientResponseDTO.setName(patientEntity.getName());
        patientResponseDTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientResponseDTO.setGender(patientEntity.getGender().getGender());
        patientResponseDTO.setEmail(patientEntity.getEmail());
        patientResponseDTO.setPhoneNumber(patientEntity.getPhoneNumber());
        return patientResponseDTO;
    }
}
