package com.dc.mapper;

import com.dc.dto.MedicalTestCreateRequestDTO;
import com.dc.dto.MedicalTestResponseDTO;
import com.dc.dto.MedicalTestUpdateRequestDTO;
import com.dc.entity.MedicalTestEntity;

public class MedicalTestMapper {
    public static MedicalTestEntity fromCreateDTOToEntity(MedicalTestCreateRequestDTO medicalTestCreateRequestDTO){
        MedicalTestEntity medicalTestEntity = new MedicalTestEntity();
//        medicalTestEntity.setDepartment(medicalTestCreateRequestDTO.getDepartment());
//        medicalTestEntity.setCategory(medicalTestCreateRequestDTO.getCategory());
        medicalTestEntity.setTestName(medicalTestCreateRequestDTO.getTestName());
        medicalTestEntity.setTestCode(medicalTestCreateRequestDTO.getTestCode());
//        medicalTestEntity.setSpecimen(medicalTestCreateRequestDTO.getSpecimen());
//        medicalTestEntity.setMethod(medicalTestCreateRequestDTO.getMethod());
        medicalTestEntity.setNormalRange(medicalTestCreateRequestDTO.getNormalRange());
//        medicalTestEntity.setUnit(medicalTestCreateRequestDTO.getUnit());
        medicalTestEntity.setPanel(medicalTestCreateRequestDTO.getPanel());
        medicalTestEntity.setPanelName(medicalTestCreateRequestDTO.getPanelName());
        return medicalTestEntity;
    }


    public static MedicalTestResponseDTO fromEntityToDTO(MedicalTestEntity medicalTestEntity){
//        MedicalTestResponseDTO medicalTestResponseDTO = new MedicalTestResponseDTO();
//        medicalTestResponseDTO.setId(medicalTestEntity.getId());
////        medicalTestResponseDTO.setCategory(medicalTestEntity.getCategory());
//        medicalTestResponseDTO.setTestName(medicalTestEntity.getTestName());
//        medicalTestResponseDTO.setActive(medicalTestEntity.getActive());
//        medicalTestResponseDTO.setNormalRange(medicalTestEntity.getNormalRange());
////        medicalTestResponseDTO.setUnit(medicalTestEntity.getUnit());
//        medicalTestResponseDTO.setCreatedByUserID(medicalTestEntity.getCreatedByUserID().toString());
//        medicalTestResponseDTO.setCreatedDate(medicalTestEntity.getCreatedDate().toString());
        return null;
    }


    public static MedicalTestEntity fromUpdateDTOToEntity(MedicalTestEntity medicalTestEntity, MedicalTestUpdateRequestDTO medicalTestUpdateRequestDTO){
//        medicalTestEntity.setCategory(medicalTestUpdateRequestDTO.getCategory());
        medicalTestEntity.setTestName(medicalTestUpdateRequestDTO.getTestName());
        medicalTestEntity.setActive(medicalTestUpdateRequestDTO.getActive());
        medicalTestEntity.setNormalRange(medicalTestUpdateRequestDTO.getNormalRange());
//        medicalTestEntity.setUnit(medicalTestUpdateRequestDTO.getUnit());
        return medicalTestEntity;
    }
}
