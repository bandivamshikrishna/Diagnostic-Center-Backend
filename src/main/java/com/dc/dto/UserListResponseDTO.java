package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserListResponseDTO {
    private String userCode;
    private String fullName;
    private String email;
    private String role;
    private Boolean active;
    private Long id;


}
