package com.dc.serviceImpl;

import com.dc.dto.*;
import com.dc.entity.MedicalTestEntity;
import com.dc.entity.UserAuthEntity;
import com.dc.exception.MedicalTestException;
import com.dc.exception.UserException;
import com.dc.mapper.MedicalTestMapper;
import com.dc.repository.*;
import com.dc.service.MedicalTestService;
import com.dc.utils.MedicalTestSpecification;
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
public class MedicalTestServiceImpl implements MedicalTestService {

    private final MedicalTestRepository medicalTestRepository;
    private final UserAuthRepository userAuthRepository;
    private final MedicalTestDepartmentRepository medicalTestDepartmentRepository;
    private final MedicalTestCategoryRepository medicalTestCategoryRepository;
    private final MedicalTestSpecimenRepository medicalTestSpecimenRepository;
    private final MedicalTestMethodRepository medicalTestMethodRepository;
    private final MedicalTestUnitRepository medicalTestUnitRepository;



    @Override
    public List<Map<String,String>> getMedicalTestDepartments() {
        return medicalTestDepartmentRepository.findAll().stream().map(
                department -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("name", department.getId().toString());
                    map.put("value", department.getDepartmentName());
                    return map;
                }
        ).toList();
    }

    @Override
    public List<Map<String, String>> getMedicalTestCategories() {
        return medicalTestCategoryRepository.findAll().stream().map(
                category -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("name", category.getId().toString());
                    map.put("value", category.getCategoryName());
                    return map;
                }
        ).toList();
    }

    @Override
    public List<Map<String, String>> getMedicalTestMethods() {
        return medicalTestMethodRepository.findAll().stream().map(
                method -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("name", method.getId().toString());
                    map.put("value", method.getMethodName());
                    return map;
                }
        ).toList();
    }

    @Override
    public List<Map<String, String>> getMedicalTestSpecimens() {
        return medicalTestSpecimenRepository.findAll().stream().map(
                specimen -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("name", specimen.getId().toString());
                    map.put("value", specimen.getSpecimenName());
                    return map;
                }
        ).toList();
    }

    @Override
    public List<Map<String, String>> getMedicalTestUnits() {
        return medicalTestUnitRepository.findAll().stream().map(
                unit -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("name", unit.getId().toString());
                    map.put("value", unit.getUnit());
                    return map;
                }
        ).toList();
    }

    @Override
    public String createMedicalTest(MedicalTestCreateRequestDTO medicalTestCreateRequestDTO, UserAuthEntity createdBy) {
//        UserAuthEntity createdByUserID = userAuthRepository.findById(createdBy.getId()).orElseThrow(
//                () -> new UserException("createdByUserID",String.format("User Not Found with ID : %d", createdBy.getId()))
//        );
//        if(medicalTestRepository.existsByTestName(medicalTestCreateRequestDTO.getTestName().toLowerCase().trim()))
//            throw new MedicalTestException("testName",String.format("Medical Test Already Exists with Name : %s", medicalTestCreateRequestDTO.getTestName()));
//        if(medicalTestRepository.existsByTestCode(medicalTestCreateRequestDTO.getTestCode().toLowerCase().trim()))
//            throw new MedicalTestException("testCode", String.format("Medical Test Code Already Exists with Code : %s", medicalTestCreateRequestDTO.getTestCode()));
//        MedicalTestEntity medicalTestEntity = MedicalTestMapper.fromCreateDTOToEntity(medicalTestCreateRequestDTO);
//        medicalTestEntity.setTestName(medicalTestEntity.getTestName().toLowerCase().trim());
//        medicalTestEntity.setTestCode(medicalTestEntity.getTestCode().toLowerCase().trim());
//        medicalTestEntity.setActive(true);
//        medicalTestEntity.setCreatedByUserID(createdByUserID);
//        medicalTestEntity.setCreatedDate(LocalDateTime.now());
//        return String.format("Test Created Successfully with ID : %d", medicalTestRepository.save(medicalTestEntity).getId());
        return "";
    }

    @Override
    public MedicalTestResponseDTO getMedicalTestByID(Long id) {
        MedicalTestEntity medicalTestEntity = medicalTestRepository.findById(id).orElseThrow(
                () -> new MedicalTestException("id",String.format("Medical Test Not Found with ID : %s", id))
        );
        return MedicalTestMapper.fromEntityToDTO(medicalTestEntity);
    }

    @Override
    public String updateMedicalTestById(Long id, MedicalTestUpdateRequestDTO medicalTestUpdateRequestDTO) {
        UserAuthEntity lastModifiedByUserID = userAuthRepository.findById(medicalTestUpdateRequestDTO.getLastModifiedByUserID()).orElseThrow(
                () -> new UserException("lastModifiedByUserID",String.format("User Not Found with ID : %d", medicalTestUpdateRequestDTO.getLastModifiedByUserID()))
        );
        MedicalTestEntity medicalTestEntity = medicalTestRepository.findById(id).orElseThrow(
                ()-> new MedicalTestException("id",String.format("Medical Test Not Found with ID : %d", id))
        );

        if(medicalTestRepository.existsByTestNameAndIdNot(medicalTestUpdateRequestDTO.getTestName().toLowerCase().trim(),id))
            throw new MedicalTestException("testName",String.format("Medical Test Already Exists with name %s", medicalTestUpdateRequestDTO.getTestName()));

        MedicalTestMapper.fromUpdateDTOToEntity(medicalTestEntity,medicalTestUpdateRequestDTO);
        medicalTestEntity.setLastModifiedByUserID(lastModifiedByUserID);
        medicalTestEntity.setLastModifiedDate(LocalDateTime.now());
        medicalTestRepository.save(medicalTestEntity);
        return String.format("Medical Test Updated Successfully with ID : %d", id);
    }

    @Override
    public PageResponseDTO<MedicalTestResponseDTO> getAllMedicalTests(UserAuthEntity userAuthEntity, String testCode, String testName, String category, String department, Date startDate, Date endDate, String filterType, Pageable pageable) {
        Specification<MedicalTestEntity> spec = MedicalTestSpecification.getMedicalTestsFilters(testCode,testName,category,department,startDate,endDate,filterType);

        Page<MedicalTestEntity> page = medicalTestRepository.findAll(spec,pageable);
        List<MedicalTestResponseDTO> medicalTestResponses = page.getContent().stream().map(MedicalTestMapper::fromEntityToDTO).toList();
        return new PageResponseDTO<>(medicalTestResponses,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
    }
}
