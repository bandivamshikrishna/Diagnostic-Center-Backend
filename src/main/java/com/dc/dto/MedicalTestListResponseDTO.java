package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalTestListResponseDTO {
    private Long id;
    private String department;
    private String category;
    private String testName;
    private String testCode;
    private Boolean active;
}
