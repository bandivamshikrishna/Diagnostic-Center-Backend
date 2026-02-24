package com.dc.service;

import com.dc.dto.*;
import com.dc.entity.UserAuthEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VendorService {
    public String createVendor(VendorCreateRequestDTO vendorCreateRequestDTO, MultipartFile logo,UserAuthEntity userAuthEntity) throws IOException;
    public List<VendorListResponseDTO> getAllActiveVendors();
    public VendorResponseDTO getVendorById(long id);
    public String updateVendorById(long id, VendorUpdateRequestDTO vendorUpdateRequestDTO);
    public Long getVendorMaxNoOfUsers(Long id);
    public String createVendorPackage(VendorCreatePackageRequestDTO vendorCreatePackageRequestDTO);
    public String manageVendorMedicalTests(VendorManageMedicalTestsDTO vendorManageMedicalTestsDTO);
}