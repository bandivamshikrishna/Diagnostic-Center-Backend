package com.dc.mapper;

import com.dc.dto.UserCreateRequestDTO;
import com.dc.dto.UserResponseDTO;
import com.dc.entity.UserAuthEntity;

public class UserAuthMapper {

    public static UserAuthEntity fromCreateDTOToEntity(UserCreateRequestDTO userCreateRequestDTO){
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setFullName(userCreateRequestDTO.getFullName());
        return userAuthEntity;
    }

    public static UserResponseDTO fromEntityToDTO(UserAuthEntity userAuthEntity){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(userAuthEntity.getEmail());
        userResponseDTO.setRole(userAuthEntity.getRole().getRoleName());
        return userResponseDTO;
    }
}
