package com.dc.mapper;


import com.dc.dto.*;
import com.dc.entity.VendorBranchEntity;
import com.dc.entity.VendorEntity;
import com.dc.entity.VendorPackageEntity;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VendorMapper {
    public static VendorEntity fromCreateDTOToEntity(VendorCreateRequestDTO vendorCreateRequestDTO) throws IOException {
        VendorEntity vendorEntity = new VendorEntity();
        vendorEntity.setName(vendorCreateRequestDTO.getName());
        vendorEntity.setEmail(vendorCreateRequestDTO.getEmail());
        vendorEntity.setAddress(vendorCreateRequestDTO.getAddress());
        vendorEntity.setPhoneNumber(vendorCreateRequestDTO.getPhoneNumber());
        vendorEntity.setActivationEndDate(vendorCreateRequestDTO.getActivationEndDate());
        vendorEntity.setMaxNoOfUsers(vendorCreateRequestDTO.getMaxNoOfUsers());

        List<VendorBranchEntity> vendorBranchEntities = vendorCreateRequestDTO.getBranches().stream().map(
                (branch) -> {
                    VendorBranchEntity vendorBranchEntity = new VendorBranchEntity();
                    vendorBranchEntity.setVendor(vendorEntity);
                    vendorBranchEntity.setBranchCode(branch.getBranchCode());
                    vendorBranchEntity.setBranchName(branch.getBranchName());
                    vendorBranchEntity.setBranchAddress(branch.getBranchAddress());
                    return vendorBranchEntity;
                }
        ).toList();
        vendorEntity.setBranches(vendorBranchEntities);
        return vendorEntity;
    }


    public static VendorListResponseDTO fromEntityToListDTO(VendorEntity vendorEntity){
        VendorListResponseDTO vendorResponseDTO = new VendorListResponseDTO();
        vendorResponseDTO.setId(vendorEntity.getId());
        vendorResponseDTO.setName(vendorEntity.getName());
        vendorResponseDTO.setEmail(vendorEntity.getEmail());
        vendorResponseDTO.setPhoneNumber(vendorEntity.getPhoneNumber());
        vendorResponseDTO.setCreatedDate(vendorEntity.getCreatedDate().toString());
        vendorResponseDTO.setActivationEndDate(vendorEntity.getActivationEndDate().toString());
        vendorResponseDTO.setVendorCode(vendorEntity.getVendorCode());
        return vendorResponseDTO;
    }


    public static VendorResponseDTO fromEntityToDTO(VendorEntity vendorEntity){
        VendorResponseDTO vendorResponseDTO = new VendorResponseDTO();
        vendorResponseDTO.setID(vendorEntity.getId());
        vendorResponseDTO.setVendorCode(vendorEntity.getVendorCode());
        vendorResponseDTO.setName(vendorEntity.getName());
        vendorResponseDTO.setEmail(vendorEntity.getEmail());
        vendorResponseDTO.setPhoneNumber(vendorEntity.getPhoneNumber());
        vendorResponseDTO.setAddress(vendorEntity.getAddress());
        vendorResponseDTO.setLogoFolderPath(vendorEntity.getLogoFolderPath());
        vendorResponseDTO.setCreatedBy(vendorEntity.getCreatedByUserID().getUsername());
        vendorResponseDTO.setCreatedDate(vendorEntity.getCreatedDate().toString());
        vendorResponseDTO.setActive(vendorEntity.isActive());
        vendorResponseDTO.setActivationEndDate(vendorEntity.getActivationEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        vendorResponseDTO.setMaxNoOfUsers(vendorEntity.getMaxNoOfUsers());
        vendorResponseDTO.setBranches(vendorEntity.getBranches().stream().map(branch ->{
            VendorBranchDTO vendorBranchDTO = new VendorBranchDTO();
            vendorBranchDTO.setBranchCode(branch.getBranchCode());
            vendorBranchDTO.setBranchName(branch.getBranchName());
            vendorBranchDTO.setBranchAddress(branch.getBranchAddress());
            return vendorBranchDTO;
        }).toList());
        return vendorResponseDTO;
    }

    public static void fromUpdateDTOToEntity(VendorEntity vendorEntity, VendorUpdateRequestDTO vendorUpdateRequestDTO){
        vendorEntity.setName(vendorUpdateRequestDTO.getName());
        vendorEntity.setEmail(vendorUpdateRequestDTO.getEmail());
        vendorEntity.setAddress(vendorUpdateRequestDTO.getAddress());
        vendorEntity.setActive(vendorUpdateRequestDTO.isActive());
        vendorEntity.setActivationEndDate(vendorUpdateRequestDTO.getActivationEndDate());
        vendorEntity.setMaxNoOfUsers(vendorUpdateRequestDTO.getMaxNoOfUsers());
    }


    public static VendorPackageEntity fromCreateDTOToEntity(VendorCreatePackageRequestDTO vendorCreatePackageRequestDTO){
        VendorPackageEntity vendorPackageEntity = new VendorPackageEntity();
        vendorPackageEntity.setPackageName(vendorCreatePackageRequestDTO.getName());
        vendorPackageEntity.setPackagePrice(vendorCreatePackageRequestDTO.getPackagePrice());
        return vendorPackageEntity;
    }
}

