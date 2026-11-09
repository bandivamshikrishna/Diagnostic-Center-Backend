package com.dc.serviceImpl;

import com.dc.dto.MedicalTestListResponseDTO;
import com.dc.dto.PageResponseDTO;
import com.dc.dto.PatientCreateRequestDTO;
import com.dc.dto.PatientResponseDTO;
import com.dc.entity.*;
import com.dc.exception.PatientException;
import com.dc.exception.VendorException;
import com.dc.mapper.MedicalTestMapper;
import com.dc.mapper.PatientMapper;
import com.dc.repository.*;
import com.dc.service.PatientService;
import com.dc.utils.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientTitleRepository patientTitleRepository;
    private final PatientGenderRepository patientGenderRepository;
    private final UserAuthRepository userAuthRepository;
    private final VendorRepository vendorRepository;
    private final VendorMedicalTestRepository vendorMedicalTestRepository;
    private final VendorPackageRepository vendorPackageRepository;




    @Override
    public String createPatient(PatientCreateRequestDTO patientCreateRequestDTO, UserAuthEntity createdBy) {

        if(patientCreateRequestDTO.getEmail() != null && !patientCreateRequestDTO.getEmail().isEmpty() && !patientCreateRequestDTO.getEmail().isBlank()){
            if(patientRepository.existsByEmailIgnoreCaseAndDateOfBirth(patientCreateRequestDTO.getEmail(),patientCreateRequestDTO.getDateOfBirth())){
                throw new PatientException("patient", "Patient Already Exists with Email and Date Of Birth");
            }
        }

        if(patientCreateRequestDTO.getPhoneNumber() != null && !patientCreateRequestDTO.getPhoneNumber().isEmpty() && !patientCreateRequestDTO.getPhoneNumber().isBlank()){
            if(patientRepository.existsByPhoneNumberAndDateOfBirth(patientCreateRequestDTO.getPhoneNumber(),patientCreateRequestDTO.getDateOfBirth())){
                throw new PatientException("patient", "Patient Already Exists with Phone Number and Date Of Birth");
            }
        }


        PatientTitleEntity title = patientTitleRepository.findByTitleCode(patientCreateRequestDTO.getTitle()).orElseThrow(
                () -> new PatientException(String.format("Invalid Title : %s", patientCreateRequestDTO.getTitle()),"titleID")
        );

        PatientGenderEntity gender = patientGenderRepository.findByGenderCode(patientCreateRequestDTO.getGender()).orElseThrow(
                ()-> new PatientException(String.format("Invalid Gender : %s", patientCreateRequestDTO.getGender()),"genderID")
        );

        UserAuthEntity createdByUser = userAuthRepository.findById(createdBy.getId()).orElseThrow(
                ()-> new PatientException(String.format("Invalid Created By User : %d", createdBy.getId()),"createdByUserID")
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
        return patientRepository.save(patientEntity).getPatientUserCode();
    }

    @Override
    public List<Map<String, String>> getPatientLovs(String type) {
        if(type.equalsIgnoreCase("title")){
            return patientTitleRepository.findAllByOrderBySequenceAsc().stream().map(
                    title->{
                        Map<String,String> map = new HashMap<>();
                        map.put("name",title.getTitleCode());
                        map.put("value",title.getTitle());
                        return map;
                    }
            ).toList();
        }
        else if(type.equalsIgnoreCase("gender")){
            return patientGenderRepository.findAllByOrderBySequenceAsc().stream().map(
                    gender->{
                        Map<String,String> map = new HashMap<>();
                        map.put("name",gender.getGenderCode());
                        map.put("value",gender.getGender());
                        return map;
                    }
            ).toList();
        }
        return List.of();
    }

    @Override
    public PatientResponseDTO getPatientDetails(String patientCode) {
        PatientEntity patient = patientRepository.findByPatientUserCode(patientCode).orElseThrow(
                ()-> new PatientException(String.format("Invalid Patient code with ID : %s", patientCode),"patientCode")
        );
        return PatientMapper.fromEntityToDTO(patient);
    }

    @Override
    public PageResponseDTO<PatientResponseDTO> getPatients(String email, Date visitDate, UserAuthEntity userAuthEntity, Pageable pageable) {
        Specification<PatientEntity> spec = PatientSpecification.getPatients(email,visitDate);
        Page<PatientEntity> page = patientRepository.findAll(spec,pageable);
        List<PatientResponseDTO> patientResponses = page.getContent().stream().map(PatientMapper::fromEntityToDTO).toList();
        return new PageResponseDTO<>(patientResponses,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
    }
}
