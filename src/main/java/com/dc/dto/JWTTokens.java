package com.dc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class JWTTokens {
    private String accessToken;
    private String refreshToken;


}
