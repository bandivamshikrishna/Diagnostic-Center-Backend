package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBasicDetails {
    private Long id;
    private String code;
    private String name;
    private String email;
}
