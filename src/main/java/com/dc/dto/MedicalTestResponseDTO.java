package com.dc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class MedicalTestResponseDTO {

    private Long id;
    private Long department;
    private Long category;
    private String testName;
    private String testCode;
    private Long specimen;
    private Long method;
    private Long unit;
    private Boolean isPanel;
    private String panelName;
    private String normalRange;

}
