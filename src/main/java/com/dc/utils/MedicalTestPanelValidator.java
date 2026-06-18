package com.dc.utils;

import com.dc.dto.MedicalTestCreateRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MedicalTestPanelValidator implements ConstraintValidator<ValidPanelMedicalTest, MedicalTestCreateRequestDTO> {
    @Override
    public boolean isValid(MedicalTestCreateRequestDTO medicalTestCreateRequestDTO, ConstraintValidatorContext constraintValidatorContext) {

        if(Boolean.TRUE.equals(medicalTestCreateRequestDTO.getPanel())){
            return medicalTestCreateRequestDTO.getPanelName() != null &&
                    !medicalTestCreateRequestDTO.getPanelName().isEmpty();
        }
        return true;
    }
}
