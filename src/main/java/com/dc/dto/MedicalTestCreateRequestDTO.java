package com.dc.dto;


import com.dc.utils.ValidPanelMedicalTest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@ValidPanelMedicalTest
@RequiredArgsConstructor
@Getter
@Setter
public class MedicalTestCreateRequestDTO {

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Test Name is required")
    private String testName;

    @NotBlank(message = "Test Code is required")
    private String testCode;

    @NotBlank(message = "Specimen is required")
    private String specimen;

    @NotBlank(message = "Method is required")
    private String method;

    @NotBlank(message = "Normal Range is required")
    private String normalRange;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotNull(message = "Is Panel is required")
    private Boolean panel;

    private String panelName;

}
