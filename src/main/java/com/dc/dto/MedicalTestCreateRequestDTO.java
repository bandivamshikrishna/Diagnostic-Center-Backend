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

    @NotNull(message = "Department is required")
    private Long department;

    @NotNull(message = "Category is required")
    private Long category;

    @NotBlank(message = "Test Name is required")
    private String testName;

    @NotNull(message = "Specimen is required")
    private Long specimen;

    @NotNull(message = "Method is required")
    private Long method;

    @NotBlank(message = "Normal Range is required")
    private String normalRange;

    @NotNull(message = "Unit is required")
    private Long unit;

    @NotNull(message = "Is Panel is required")
    private Boolean panel;

    private String panelName;

}
