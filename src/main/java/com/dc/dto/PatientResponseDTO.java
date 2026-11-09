package com.dc.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientResponseDTO{
    private String title;

    private String name;

    private Date dateOfBirth;

    private String gender;

    private String email;

    private String phoneNumber;
}
