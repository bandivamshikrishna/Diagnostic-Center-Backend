package com.dc.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MedicalTestResponseDTO {

    private Long id;
    private String category;
    private String testName;
    private String normalRange;
    private String unit;
    private String createdByUserID;
    private String createdDate;
    private Boolean active;

}
