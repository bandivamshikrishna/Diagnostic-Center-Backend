package com.dc.mapper;

import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;

import java.util.HashMap;
import java.util.Map;


public class UserAuthMapper {

    public static UserAuthEntity fromCreateDTOToEntity(UserCreateOrUpdateRequestDTO userCreateRequestDTO){
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setFullName(userCreateRequestDTO.getFullName());
        return userAuthEntity;
    }

    public static UserResponseDTO fromEntityToDTO(UserAuthEntity userAuthEntity){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserBasicDetails user = new UserBasicDetails();
        user.setId(userAuthEntity.getId());
        user.setCode(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getUserCode());
        user.setName(userAuthEntity.getFullName());
        user.setEmail(userAuthEntity.getEmail());

        KeyValuePair role = new KeyValuePair();
        role.setId(userAuthEntity.getRole().getId());
        role.setKey(userAuthEntity.getRole().getRoleCode());
        role.setValue(userAuthEntity.getRole().getRoleName());

        KeyValuePair vendor = new KeyValuePair();
        vendor.setId(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? 0 : userAuthEntity.getVendorID().getId());
        vendor.setKey(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getVendorID().getVendorCode());
        vendor.setValue(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getVendorID().getVendorCode());

        KeyValuePair branch = new KeyValuePair();
        branch.setId(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? 0 : userAuthEntity.getVendorBranch().getId());
        branch.setKey(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getVendorBranch().getBranchCode());
        branch.setValue(userAuthEntity.getRole().getRoleCode().equalsIgnoreCase("ad") ? "" : userAuthEntity.getVendorBranch().getBranchName());

        userResponseDTO.setUser(user);
        userResponseDTO.setRole(role);
        userResponseDTO.setVendor(vendor);
        userResponseDTO.setBranch(branch);
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
