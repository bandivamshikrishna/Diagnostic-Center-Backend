package com.dc.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatientValidator.class)
public @interface ValidatePatient {

    String message() default "Either Email or Phone Number is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
