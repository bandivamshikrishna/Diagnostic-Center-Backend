package com.dc.mapper;

import com.dc.dto.UserCreateOrUpdateRequestDTO;
import com.dc.dto.UserListResponseDTO;
import com.dc.dto.UserResponseDTO;
import com.dc.entity.UserAuthEntity;


public class UserAuthMapper {

    public static UserAuthEntity fromCreateDTOToEntity(UserCreateOrUpdateRequestDTO userCreateRequestDTO){
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setFullName(userCreateRequestDTO.getFullName());
        return userAuthEntity;
    }

    public static UserResponseDTO fromEntityToDTO(UserAuthEntity userAuthEntity){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(userAuthEntity.getEmail());
        userResponseDTO.setRole(userAuthEntity.getRole().getRoleCode());
        userResponseDTO.setVendor(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getVendorID().getVendorCode());
        userResponseDTO.setFullName(userAuthEntity.getFullName());
        userResponseDTO.setId(userAuthEntity.getId());
        userResponseDTO.setUserCode(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getUserCode());
        userResponseDTO.setVendorBranch(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getVendorBranch().getBranchCode());
        userResponseDTO.setRoleFullName(userAuthEntity.getRole().getRoleName());
        return userResponseDTO;
    }


    public static UserListResponseDTO fromEntityToListDTO(UserAuthEntity userAuthEntity){
        UserListResponseDTO userListResponseDTO = new UserListResponseDTO();
        userListResponseDTO.setFullName(userAuthEntity.getFullName());
        userListResponseDTO.setEmail(userAuthEntity.getEmail());
        userListResponseDTO.setRole(userAuthEntity.getRole().getRoleName());
        userListResponseDTO.setUserCode(userAuthEntity.getUserCode());
        userListResponseDTO.setId(userAuthEntity.getId());
        userListResponseDTO.setActive(userAuthEntity.isEnabled());
        return userListResponseDTO;
    }
}
