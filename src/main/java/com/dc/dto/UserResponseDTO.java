package com.dc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class UserResponseDTO {
    private UserBasicDetails user;
    private KeyValuePair role;
    private KeyValuePair vendor;
    private KeyValuePair branch;
}
