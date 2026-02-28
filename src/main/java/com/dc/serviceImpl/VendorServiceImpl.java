package com.dc.serviceImpl;


import com.dc.dto.*;
import com.dc.entity.*;
import com.dc.exception.*;
import com.dc.mapper.VendorMapper;
import com.dc.repository.*;
import com.dc.service.VendorService;
import com.dc.utils.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserAuthRepository userAuthRepository;
    private final VendorPackageRepository vendorPackageRepository;
    private final MedicalTestRepository medicalTestRepository;
    private final VendorMedicalTestRepository vendorMedicalTestRepository;

    public VendorServiceImpl(VendorRepository vendorRepository,UserAuthRepository userAuthRepository,
                             VendorPackageRepository vendorPackageRepository,MedicalTestRepository medicalTestRepository,
                             VendorMedicalTestRepository vendorMedicalTestRepository){
        this.vendorRepository = vendorRepository;
        this.userAuthRepository = userAuthRepository;
        this.vendorPackageRepository = vendorPackageRepository;
        this.medicalTestRepository = medicalTestRepository;
        this.vendorMedicalTestRepository = vendorMedicalTestRepository;
    }

    @Override
    public String createVendor(VendorCreateRequestDTO vendorCreateRequestDTO, MultipartFile logo, UserAuthEntity userAuthEntity, String uuid) throws IOException {
        UserAuthEntity createdByUserID = userAuthRepository.findById(userAuthEntity.getId()).orElseThrow(
                () -> new UserException("createdByUserID",String.format("User Not Found with ID : %d", userAuthEntity.getId())));
        if(vendorRepository.existsByEmail(vendorCreateRequestDTO.getEmail().toLowerCase().trim()))
            throw new VendorException("email",String.format("Vendor Already Exists with Email ID : %s",vendorCreateRequestDTO.getEmail()));
        if(vendorRepository.existsByPhoneNumber(vendorCreateRequestDTO.getPhoneNumber()))
            throw new VendorException("phoneNumber","Phone Number Already Exists");
        validateVendorBranches(vendorCreateRequestDTO.getBranches());
        VendorEntity vendorEntity = VendorMapper.fromCreateDTOToEntity(vendorCreateRequestDTO);
        vendorEntity.setUuid(uuid);
        vendorEntity.setCreatedByUserID(createdByUserID);
        vendorEntity.setCreatedDate(LocalDateTime.now());
        vendorEntity.setEmail(vendorEntity.getEmail().toLowerCase());
        vendorEntity.setActive(true);
        vendorEntity.setLogoFolderPath(FileStorageService.storeFile(logo,"img"));
        vendorEntity.setVendorCode("V"+String.format("%010d", vendorRepository.getNextVendorCode()));
        VendorEntity savedVendorEntity = vendorRepository.save(vendorEntity);

        //Email Notification
        return "Vendor Created Successfully with ID : "+savedVendorEntity.getVendorCode();
    }

    @Override
    public List<VendorListResponseDTO> getAllActiveVendors() {
        List<VendorEntity> vendorEntities = vendorRepository.findAll();
        List<VendorListResponseDTO> vendorResponses = new ArrayList<>();

        if(!vendorEntities.isEmpty()){
            return vendorEntities.stream().map(
                    VendorMapper::fromEntityToListDTO
            ).toList();
        }
        return vendorResponses;
    }

    @Override
    public VendorResponseDTO getVendorById(long id) {
        VendorEntity vendorEntity = vendorRepository.findById(id).orElseThrow(
                () -> new VendorException("ID",String.format("Vendor with ID : %d is not Found", id)));
        return VendorMapper.fromEntityToDTO(vendorEntity);
    }

    @Override
    public String updateVendorById(long id, VendorUpdateRequestDTO vendorUpdateRequestDTO) {
        UserAuthEntity lastModifiedByUserID = userAuthRepository.findById(vendorUpdateRequestDTO.getLastModifiedByUserID()).orElseThrow(
                () -> new UserException("lastModifiedByUserID",String.format("User Not Found with ID : %d", vendorUpdateRequestDTO.getLastModifiedByUserID())));

        VendorEntity vendorEntity = vendorRepository.findById(id).orElseThrow(
                () -> new VendorException("ID",String.format("Vendor with ID : %d is not Found", id))
        );

        if(vendorRepository.existsByEmailAndIdNot(vendorUpdateRequestDTO.getEmail().toLowerCase().trim(),id))
            throw new VendorException("email",String.format("Vendor Already Exists with Email ID : %s", vendorUpdateRequestDTO.getEmail()));

        if(vendorRepository.existsByPhoneNumberAndIdNot(vendorUpdateRequestDTO.getPhoneNumber(), id))
            throw new VendorException("phoneNumber",String.format("Vendor Already Exists with Phone Number : %s", vendorUpdateRequestDTO.getPhoneNumber()));
        VendorMapper.fromUpdateDTOToEntity(vendorEntity,vendorUpdateRequestDTO);
        vendorEntity.setLastModifiedByUserID(lastModifiedByUserID);
        vendorEntity.setLastModifiedDate(LocalDateTime.now());
        vendorEntity.setEmail(vendorEntity.getEmail().toLowerCase());
        vendorRepository.save(vendorEntity);

        //Email Notification
        return String.format("Vendor Updated Successfully with ID : %d", id);
    }

    @Override
    public Long getVendorMaxNoOfUsers(Long id) {
        VendorEntity vendorEntity = vendorRepository.findById(id).orElseThrow(
                ()-> new VendorException("ID",String.format("Vendor with ID : %d is not Found", id))
        );
        return vendorEntity.getMaxNoOfUsers();
    }

    @Override
    public String createVendorPackage(VendorCreatePackageRequestDTO vendorCreatePackageRequestDTO) {

        VendorEntity vendor = vendorRepository.findById(vendorCreatePackageRequestDTO.getVendorID()).orElseThrow(
                ()-> new VendorException("vendorID", String.format("Vendor Not Found with ID : %d", vendorCreatePackageRequestDTO.getVendorID()))
        );
        VendorPackageEntity vendorPackageEntity = VendorMapper.fromCreateDTOToEntity(vendorCreatePackageRequestDTO);
        List<VendorPackageMedicalTestEntity> medicalTests = new ArrayList<>();

        for (MedicalTestsIDTO test : vendorCreatePackageRequestDTO.getMedicalTests()){
            MedicalTestEntity medicalTestEntity = medicalTestRepository.findById(test.getId()).orElseThrow(
                    ()->new MedicalTestException("id",String.format("Medical Test Not Found with ID: %d", test.getId()))
            );
            VendorPackageMedicalTestEntity medicalTest = new VendorPackageMedicalTestEntity();
            medicalTest.setPackageID(vendorPackageEntity);
            medicalTest.setMedicalTest(medicalTestEntity);
            medicalTests.add(medicalTest);
        }

        vendorPackageEntity.setMedicalTest(medicalTests);
        vendorPackageEntity.setVendor(vendor);
        return String.format("Package Created Successfully with ID : %d", vendorPackageRepository.save(vendorPackageEntity).getId());
    }

    @Override
    public String manageVendorMedicalTests(VendorManageMedicalTestsDTO vendorManageMedicalTestsDTO){
        VendorEntity vendorEntity = vendorRepository.findById(vendorManageMedicalTestsDTO.getVendorID()).orElseThrow(
                () -> new VendorException("vendorID", String.format("Vendor Not Found with ID : %d", vendorManageMedicalTestsDTO.getVendorID()))
        );
        List<VendorMedicalTestEntity> vendorMedicalTestEntities = new ArrayList<>();
        for(VendorMedicalTestsDTO test : vendorManageMedicalTestsDTO.getMedicalTests()){
            MedicalTestEntity medicalTest = medicalTestRepository.findById(test.getMedicalTestID()).orElseThrow(
                    ()-> new MedicalTestException("testID", String.format("Medical Test Not Found with ID : %d", test.getMedicalTestID()))
            );

            VendorMedicalTestEntity vendorMedicalTestEntity = new VendorMedicalTestEntity();
            vendorMedicalTestEntity.setVendor(vendorEntity);
            vendorMedicalTestEntity.setMedicalTestPrice(test.getTestPrice());
            vendorMedicalTestEntity.setMedicalTest(medicalTest);

            vendorMedicalTestEntities.add(vendorMedicalTestEntity);
        }
        vendorMedicalTestRepository.saveAll(vendorMedicalTestEntities);
        return "Tests Updated successfully";
    }



    public void validateVendorBranches(List<VendorBranchDTO> vendorBranchDTO){
        Set<String> branches = new HashSet<>();
        for (VendorBranchDTO branchDTO : vendorBranchDTO){
            if(!branches.add(branchDTO.getBranchCode()))
                throw new VendorException("branchCode", String.format("Duplicate Branch Code %s", branchDTO.getBranchCode()));
        }
    }

}

