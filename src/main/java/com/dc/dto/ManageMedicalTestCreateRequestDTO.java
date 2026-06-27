package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ManageMedicalTestCreateRequestDTO {
    private Long testID;
    private Double testPrice;
}
