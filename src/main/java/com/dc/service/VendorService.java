package com.dc.service;

import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VendorService {
    String createVendor(VendorCreateRequestDTO vendorCreateRequestDTO, MultipartFile logo,UserAuthEntity userAuthEntity, String uuid) throws IOException;
    List<VendorListResponseDTO> getAllActiveVendors();
    VendorResponseDTO getVendorById(long id);
    String updateVendorById(long id, VendorUpdateRequestDTO vendorUpdateRequestDTO, MultipartFile logo, UserAuthEntity userAuthEntity,String uuid);
    Long getVendorMaxNoOfUsers(Long id);
    String createVendorPackage(VendorCreatePackageRequestDTO vendorCreatePackageRequestDTO);
    String manageVendorMedicalTests(VendorManageMedicalTestsDTO vendorManageMedicalTestsDTO);
    List<Map<String,String>> getListOfVendors();
    List<Map<String, String>> getVendorBranches(String vendorCode);
}