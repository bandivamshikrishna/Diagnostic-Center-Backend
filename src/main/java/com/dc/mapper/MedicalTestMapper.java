package com.dc.mapper;

import com.dc.dto.MedicalTestListResponseDTO;
import com.dc.dto.MedicalTestResponseDTO;
import com.dc.entity.MedicalTestEntity;

public class MedicalTestMapper {


    public static MedicalTestResponseDTO fromEntityToDTO(MedicalTestEntity medicalTestEntity){

        MedicalTestResponseDTO medicalTestResponseDTO = new MedicalTestResponseDTO();
        medicalTestResponseDTO.setId(medicalTestEntity.getId());
        medicalTestResponseDTO.setDepartment(medicalTestEntity.getDepartment().getId());
        medicalTestResponseDTO.setCategory(medicalTestEntity.getCategory().getId());
        medicalTestResponseDTO.setTestName(medicalTestEntity.getTestName());
        medicalTestResponseDTO.setTestCode(medicalTestEntity.getTestCode());
        medicalTestResponseDTO.setSpecimen(medicalTestEntity.getSpecimen().getId());
        medicalTestResponseDTO.setMethod(medicalTestEntity.getMethod().getId());
        medicalTestResponseDTO.setUnit(medicalTestEntity.getUnit().getId());
        medicalTestResponseDTO.setIsPanel(medicalTestEntity.getIsPanel());
        medicalTestResponseDTO.setPanelName(medicalTestEntity.getPanelName());
        medicalTestResponseDTO.setNormalRange(medicalTestEntity.getNormalRange());
        return medicalTestResponseDTO;
    }

    public static MedicalTestListResponseDTO fromEntityToListDTO(MedicalTestEntity medicalTestEntity){

        MedicalTestListResponseDTO medicalTestResponseDTO = new MedicalTestListResponseDTO();
        medicalTestResponseDTO.setId(medicalTestEntity.getId());
        medicalTestResponseDTO.setCategory(medicalTestEntity.getCategory().getCategoryName());
        medicalTestResponseDTO.setTestName(medicalTestEntity.getTestName());
        medicalTestResponseDTO.setActive(medicalTestEntity.getActive());
        medicalTestResponseDTO.setDepartment(medicalTestEntity.getDepartment().getDepartmentName());
        medicalTestResponseDTO.setTestCode(medicalTestEntity.getTestCode());
        return medicalTestResponseDTO;
    }
}
