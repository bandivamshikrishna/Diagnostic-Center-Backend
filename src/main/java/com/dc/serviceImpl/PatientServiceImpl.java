package com.dc.serviceImpl;

import com.dc.dto.PatientCreateRequestDTO;
import com.dc.entity.*;
import com.dc.exception.PatientException;
import com.dc.exception.VendorException;
import com.dc.mapper.PatientMapper;
import com.dc.repository.*;
import com.dc.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientTitleRepository patientTitleRepository;
    private final PatientGenderRepository patientGenderRepository;
    private final UserAuthRepository userAuthRepository;
    private final VendorRepository vendorRepository;
    private final VendorMedicalTestRepository vendorMedicalTestRepository;
    private final VendorPackageRepository vendorPackageRepository;

    public PatientServiceImpl(PatientRepository patientRepository,
                              PatientTitleRepository patientTitleRepository,
                              PatientGenderRepository patientGenderRepository,
                              UserAuthRepository userAuthRepository,
                              VendorRepository vendorRepository,
                              VendorMedicalTestRepository vendorMedicalTestRepository,VendorPackageRepository vendorPackageRepository){
        this.patientRepository = patientRepository;
        this.patientTitleRepository = patientTitleRepository;
        this.patientGenderRepository = patientGenderRepository;
        this.userAuthRepository = userAuthRepository;
        this.vendorRepository = vendorRepository;
        this.vendorMedicalTestRepository = vendorMedicalTestRepository;
        this.vendorPackageRepository = vendorPackageRepository;
    }


    @Override
    public String createPatient(PatientCreateRequestDTO patientCreateRequestDTO, UserAuthEntity createdBy) {

        if((patientCreateRequestDTO.getEmail() == null || patientCreateRequestDTO.getEmail().isBlank())
            && (patientCreateRequestDTO.getPhoneNumber() == null || patientCreateRequestDTO.getPhoneNumber().isBlank())){
            throw new PatientException("email", "Email Or Phone Number is required");
        }

        if(patientCreateRequestDTO.getEmail() != null && !patientCreateRequestDTO.getEmail().isEmpty() && !patientCreateRequestDTO.getEmail().isBlank()){
            if(patientRepository.existsByEmailIgnoreCaseAndDateOfBirth(patientCreateRequestDTO.getEmail(),patientCreateRequestDTO.getDob())){
                throw new PatientException("patient", "Patient Already Exists with Email and Date Of Birth");
            }
        }

        if(patientCreateRequestDTO.getPhoneNumber() != null && !patientCreateRequestDTO.getPhoneNumber().isEmpty() && !patientCreateRequestDTO.getPhoneNumber().isBlank()){
            if(patientRepository.existsByPhoneNumberAndDateOfBirth(patientCreateRequestDTO.getPhoneNumber(),patientCreateRequestDTO.getDob())){
                throw new PatientException("patient", "Patient Already Exists with Phone Number and Date Of Birth");
            }
        }


        PatientTitleEntity title = patientTitleRepository.findById(patientCreateRequestDTO.getTitleID()).orElseThrow(
                () -> new PatientException(String.format("Invalid Title ID : %d", patientCreateRequestDTO.getTitleID()),"titleID")
        );

        PatientGenderEntity gender = patientGenderRepository.findById(patientCreateRequestDTO.getGenderID()).orElseThrow(
                ()-> new PatientException(String.format("Invalid Gender ID : %d", patientCreateRequestDTO.getGenderID()),"genderID")
        );

        UserAuthEntity createdByUser = userAuthRepository.findById(createdBy.getId()).orElseThrow(
                ()-> new PatientException(String.format("Invalid Created By User ID : %d", createdBy.getId()),"createdByUserID")
        );

        VendorEntity vendorEntity = vendorRepository.findById(createdBy.getVendorID().getId()).orElseThrow(
                () -> new VendorException("vendorID", String.format("Vendor not Found with ID : %d", createdBy.getVendorID().getId()))
        );
        PatientEntity patientEntity = PatientMapper.fromCreateDTOToEntity(patientCreateRequestDTO);
        patientEntity.setPatientTitle(title);
        patientEntity.setGender(gender);
        patientEntity.setVendorID(vendorEntity);
        patientEntity.setCreatedBy(createdByUser);
        patientEntity.setCreatedDateTime(LocalDateTime.now());
        patientEntity.setPatientUserCode("P"+String.format("%010d", patientRepository.getNextPatientUserCode()));
        return String.format("Patient Created Successfully with ID : %s", patientRepository.save(patientEntity).getPatientUserCode());
    }
}
