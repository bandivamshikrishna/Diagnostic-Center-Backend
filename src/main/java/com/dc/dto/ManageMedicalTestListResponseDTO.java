package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManageMedicalTestListResponseDTO {
    private Long id;
    private String testName;
    private String department;
    private String category;
    private Double testPrice;
    private Boolean selected;
}
