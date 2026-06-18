package com.dc.service;

import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserAuthService {
    String createUser(UserCreateOrUpdateRequestDTO userCreateRequestDTO, UserAuthEntity userAuthEntity);
    void setUserPassword(String token,String password);
    JWTTokens loginUser(UserLoginRequestDTO userLoginRequestDTO);
    UserResponseDTO getLoggedInUserDetails(@AuthenticationPrincipal UserAuthEntity userAuthEntity);
    List<Map<String, String>> getUserRoles();
    PageResponseDTO<UserListResponseDTO> getAllUsers(UserAuthEntity userAuthEntity, String userCode, String name, String email,
                                          String roleCode, Date startDate, Date endDate,String filterType,Pageable pageable);
    String activateOrDeActivateUser(Long ID);
    String unLockUser(Long id);
    String updateUser(UserCreateOrUpdateRequestDTO userUpdateRequestDTO, Long id, UserAuthEntity modifiedBy);
    UserResponseDTO getSpecificUserDetails(Long id);
}

