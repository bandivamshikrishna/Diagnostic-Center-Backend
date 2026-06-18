package com.dc.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MedicalTestPanelValidator.class)
public @interface ValidPanelMedicalTest {

    String message() default "Panel Name is required when isPanel is Yes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
