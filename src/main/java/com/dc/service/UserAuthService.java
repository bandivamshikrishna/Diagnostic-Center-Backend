package com.dc.service;

import com.dc.dto.JWTTokens;
import com.dc.dto.UserCreateRequestDTO;
import com.dc.dto.UserLoginRequestDTO;
import com.dc.dto.UserResponseDTO;
import com.dc.entity.UserAuthEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Map;

public interface UserAuthService {
    String createUser(UserCreateRequestDTO userCreateRequestDTO, UserAuthEntity userAuthEntity);
    void setUserPassword(String token,String password);
    JWTTokens loginUser(UserLoginRequestDTO userLoginRequestDTO);
    UserResponseDTO getUserDetails(@AuthenticationPrincipal UserAuthEntity userAuthEntity);
    List<Map<String, String>> getUserRoles();
}

