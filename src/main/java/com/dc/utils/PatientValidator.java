package com.dc.utils;

import com.dc.dto.PatientCreateRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatientValidator implements ConstraintValidator<ValidatePatient, PatientCreateRequestDTO> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = "^[6-9]\\d{9}$";

    @Override
    public boolean isValid(PatientCreateRequestDTO patientCreateRequestDTO, ConstraintValidatorContext validator) {
        String email = patientCreateRequestDTO.getEmail();
        String phoneNumber = patientCreateRequestDTO.getPhoneNumber();

        boolean hasEmail = email!=null && !email.isBlank();
        boolean hasPhoneNumber = phoneNumber!=null && !phoneNumber.isBlank();

        if(!hasEmail && !hasPhoneNumber){
            validator.disableDefaultConstraintViolation();
            validator.buildConstraintViolationWithTemplate("Either Email or Phone Number is required").addPropertyNode("email").addConstraintViolation();
            return false;
        }

        if(hasEmail && !email.matches(EMAIL_REGEX)){
            validator.disableDefaultConstraintViolation();
            validator.buildConstraintViolationWithTemplate("Please Enter a valid Email ID").addPropertyNode("email").addConstraintViolation();
            return false;
        }
        if(hasPhoneNumber && !phoneNumber.matches(PHONE_NUMBER_REGEX)){
            validator.disableDefaultConstraintViolation();
            validator.buildConstraintViolationWithTemplate("Please Enter a Valid Phone Number").addPropertyNode("phoneNumber").addConstraintViolation();
            return false;
        }
        return true;
    }
}
