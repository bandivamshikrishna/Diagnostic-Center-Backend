package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {
    private String email;
    private String role;
    private String vendor;
    private String userCode;
    private Long id;
    private String fullName;
    private String vendorBranch;
    private String roleFullName;


}
