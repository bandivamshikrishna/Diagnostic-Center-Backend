package com.dc.dto;

import com.dc.utils.ValidatePatient;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;


@ValidatePatient
@Getter
@Setter
public class PatientCreateRequestDTO {

    @NotNull(message = "Title is required")
    private String title;

    @NotBlank(message = "Patient name is required")
    private String name;

    @NotNull(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @NotNull(message = "Gender is required")
    private String gender;

    private String email;

    private String phoneNumber;



}
