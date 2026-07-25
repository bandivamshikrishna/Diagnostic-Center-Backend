package com.dc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;



@Getter
@Setter
public class PatientCreateRequestDTO {

    @NotNull(message = "Title ID is required")
    private Long titleID;

    @NotBlank(message = "Patient name is required")
    private String patientName;

    @NotNull(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dob;

    @NotNull(message = "Gender is required")
    private Long genderID;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Please enter a valid email address"
    )
    private String email;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String phoneNumber;


    private Long doctorID;


    @NotNull(message = "Sample Collected Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date sampleCollectedDate;

    @NotNull(message = "Sample Collected Time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sampleCollectedTime;

    @NotNull(message = "Reported Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date reportedDate;

    @NotNull(message = "Reported Time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime reportedTime;

}
