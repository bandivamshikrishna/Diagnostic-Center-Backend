package com.dc.serviceImpl;

import com.dc.dto.*;
import com.dc.entity.*;
import com.dc.exception.MedicalTestException;
import com.dc.exception.UserException;
import com.dc.exception.VendorException;
import com.dc.mapper.MedicalTestMapper;
import com.dc.repository.*;
import com.dc.service.MedicalTestService;
import com.dc.utils.ManageMedicalTestSpecification;
import com.dc.utils.MedicalTestSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final VendorMedicalTestRepository vendorMedicalTestRepository;
    private final VendorRepository vendorRepository;



    @Override
    public List<Map<String,String>> getMedicalTestLovs(String type) {
        if(Objects.equals(type, "DEPARTMENTS")){
            return medicalTestDepartmentRepository.findAll().stream().map(
                    department -> {
                        Map<String,String> map = new HashMap<>();
                        map.put("name", department.getId().toString());
                        map.put("value", department.getDepartmentName());
                        return map;
                    }
            ).toList();
        }
        else if(Objects.equals(type,"CATEGORIES")){
            return medicalTestCategoryRepository.findAll().stream().map(
                    category -> {
                        Map<String,String> map = new HashMap<>();
                        map.put("name", category.getId().toString());
                        map.put("value", category.getCategoryName());
                        return map;
                    }
            ).toList();
        }
        else if(Objects.equals(type,"METHODS")){
            return medicalTestMethodRepository.findAll().stream().map(
                    method -> {
                        Map<String,String> map = new HashMap<>();
                        map.put("name", method.getId().toString());
                        map.put("value", method.getMethodName());
                        return map;
                    }
            ).toList();
        }
        else if(Objects.equals(type,"SPECIMENS")){
            return medicalTestSpecimenRepository.findAll().stream().map(
                    specimen -> {
                        Map<String,String> map = new HashMap<>();
                        map.put("name", specimen.getId().toString());
                        map.put("value", specimen.getSpecimenName());
                        return map;
                    }
            ).toList();
        }
        else if(Objects.equals(type,"UNITS")){
            return medicalTestUnitRepository.findAll().stream().map(
                    unit -> {
                        Map<String,String> map = new HashMap<>();
                        map.put("name", unit.getId().toString());
                        map.put("value", unit.getUnit());
                        return map;
                    }
            ).toList();
        }
        else
            return null;
    }

    @Override
    public String createMedicalTest(MedicalTestCreateRequestDTO medicalTestCreateRequestDTO, UserAuthEntity createdBy) {
        MedicalTestDepartmentEntity medicalTestDepartment = medicalTestDepartmentRepository.findById(
                medicalTestCreateRequestDTO.getDepartment()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Department")
        );

        MedicalTestCategoryEntity medicalTestCategory = medicalTestCategoryRepository.findById(
                medicalTestCreateRequestDTO.getCategory()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Category")
        );

        MedicalTestSpecimenEntity medicalTestSpecimen = medicalTestSpecimenRepository.findById(
                medicalTestCreateRequestDTO.getSpecimen()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Specimen")
        );

        MedicalTestMethodEntity medicalTestMethod = medicalTestMethodRepository.findById(
                medicalTestCreateRequestDTO.getMethod()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Method")
        );

        MedicalTestUnitEntity medicalTestUnit = medicalTestUnitRepository.findById(
                medicalTestCreateRequestDTO.getUnit()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Unit")
        );

        UserAuthEntity createdByUserID = userAuthRepository.findById(createdBy.getId()).orElseThrow(
                () -> new UserException("createdByUserID",String.format("User Not Found with ID : %d", createdBy.getId()))
        );

        if(medicalTestRepository.existsByTestNameIgnoreCase(medicalTestCreateRequestDTO.getTestName()))
            throw new MedicalTestException("testName", String.format("Medical Test Already Exists with name %s", medicalTestCreateRequestDTO.getTestName()));

        MedicalTestEntity medicalTest = new MedicalTestEntity();
        medicalTest.setDepartment(medicalTestDepartment);
        medicalTest.setCategory(medicalTestCategory);
        medicalTest.setPanel(medicalTestCreateRequestDTO.getPanel());
        medicalTest.setPanelName(medicalTestCreateRequestDTO.getPanelName());
        medicalTest.setTestName(medicalTestCreateRequestDTO.getTestName());
        medicalTest.setTestCode('T'+String.format("%010d", medicalTestRepository.getNextTestCode()));
        medicalTest.setSpecimen(medicalTestSpecimen);
        medicalTest.setMethod(medicalTestMethod);
        medicalTest.setActive(true);
        medicalTest.setUnit(medicalTestUnit);
        medicalTest.setNormalRange(medicalTestCreateRequestDTO.getNormalRange());
        medicalTest.setCreatedDate(LocalDateTime.now());
        medicalTest.setCreatedByUserID(createdByUserID);
        String testCode = medicalTestRepository.save(medicalTest).getTestCode();
        return String.format("Test Created successfully with ID : %s", testCode);
    }

    @Override
    public MedicalTestResponseDTO getMedicalTestByID(Long id) {
        MedicalTestEntity medicalTestEntity = medicalTestRepository.findById(id).orElseThrow(
                () -> new MedicalTestException("id",String.format("Medical Test Not Found with ID : %s", id))
        );
        return MedicalTestMapper.fromEntityToDTO(medicalTestEntity);
    }

    @Override
    public String updateMedicalTestById(Long id, MedicalTestCreateRequestDTO medicalTestUpdateRequestDTO,UserAuthEntity updatedBy) {

        MedicalTestEntity medicalTestEntity = medicalTestRepository.findById(id).orElseThrow(
                ()-> new MedicalTestException("id",String.format("Medical Test Not Found with ID : %d", id))
        );

        MedicalTestDepartmentEntity medicalTestDepartment = medicalTestDepartmentRepository.findById(
                medicalTestUpdateRequestDTO.getDepartment()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Department")
        );

        MedicalTestCategoryEntity medicalTestCategory = medicalTestCategoryRepository.findById(
                medicalTestUpdateRequestDTO.getCategory()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Category")
        );

        MedicalTestSpecimenEntity medicalTestSpecimen = medicalTestSpecimenRepository.findById(
                medicalTestUpdateRequestDTO.getSpecimen()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Specimen")
        );

        MedicalTestMethodEntity medicalTestMethod = medicalTestMethodRepository.findById(
                medicalTestUpdateRequestDTO.getMethod()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Method")
        );

        MedicalTestUnitEntity medicalTestUnit = medicalTestUnitRepository.findById(
                medicalTestUpdateRequestDTO.getUnit()).orElseThrow(
                ()-> new MedicalTestException("id", "Invalid Unit")
        );

        UserAuthEntity lastModifiedByUserID = userAuthRepository.findById(updatedBy.getId()).orElseThrow(
                () -> new UserException("lastModifiedByUserID",String.format("User Not Found with ID : %d", updatedBy.getId()))
        );


        if(medicalTestRepository.existsByTestNameIgnoreCaseAndIdNot(medicalTestUpdateRequestDTO.getTestName().toLowerCase().trim(),id))
            throw new MedicalTestException("testName",String.format("Medical Test Already Exists with name %s", medicalTestUpdateRequestDTO.getTestName()));


        medicalTestEntity.setDepartment(medicalTestDepartment);
        medicalTestEntity.setCategory(medicalTestCategory);
        medicalTestEntity.setMethod(medicalTestMethod);
        medicalTestEntity.setSpecimen(medicalTestSpecimen);
        medicalTestEntity.setUnit(medicalTestUnit);
        medicalTestEntity.setTestName(medicalTestUpdateRequestDTO.getTestName());
        medicalTestEntity.setNormalRange(medicalTestUpdateRequestDTO.getNormalRange());
        medicalTestEntity.setIsPanel(medicalTestUpdateRequestDTO.getPanel());
        medicalTestEntity.setPanelName(medicalTestUpdateRequestDTO.getPanelName());
        medicalTestEntity.setLastModifiedByUserID(lastModifiedByUserID);
        medicalTestEntity.setLastModifiedDate(LocalDateTime.now());
        medicalTestRepository.save(medicalTestEntity);
        return String.format("Medical Test Updated Successfully with ID : %s", medicalTestEntity.getTestCode());
    }

    @Override
    public PageResponseDTO<MedicalTestListResponseDTO> getAllMedicalTests(UserAuthEntity userAuthEntity, String testCode, String testName, String category, String department, Date startDate, Date endDate, String filterType, Pageable pageable) {
        Specification<MedicalTestEntity> spec = MedicalTestSpecification.getMedicalTestsFilters(testCode,testName,category,department,startDate,endDate,filterType);

        Page<MedicalTestEntity> page = medicalTestRepository.findAll(spec,pageable);
        List<MedicalTestListResponseDTO> medicalTestResponses = page.getContent().stream().map(MedicalTestMapper::fromEntityToListDTO).toList();
        return new PageResponseDTO<>(medicalTestResponses,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
    }

    @Override
    public String activateOrDeActivateMedicalTest(Long id) {
        MedicalTestEntity medicalTest = medicalTestRepository.findById(id).orElseThrow(
                ()-> new MedicalTestException("id", String.format("Test Not found with ID : %s", id))
        );
        medicalTest.setActive(!medicalTest.getActive());
        medicalTestRepository.save(medicalTest);
        return "Test Updated successfully";
    }

    @Override
    public PageResponseDTO<ManageMedicalTestListResponseDTO> manageMedicalTests(UserAuthEntity userAuthEntity, String testName, String category, String department, Pageable pageable) {
        Specification<MedicalTestEntity> spec = ManageMedicalTestSpecification.getMedicalTests(testName,category,department);

        Page<MedicalTestEntity> page = medicalTestRepository.findAll(spec,pageable);
        List<Long> testIds = page.getContent().stream().map(MedicalTestEntity::getId).toList();
        List<VendorMedicalTestEntity> vendorTests = vendorMedicalTestRepository.findByVendorIdAndMedicalTestIds(userAuthEntity.getVendorID().getId(), testIds);
        Map<Long, VendorMedicalTestEntity> selectedTestsMap = vendorTests.stream().collect(
                Collectors.toMap(
                        v-> v.getMedicalTest().getId(),
                        Function.identity()
                )
        );
        List<ManageMedicalTestListResponseDTO> response = page.map(test ->{
            VendorMedicalTestEntity vendorMedicalTestEntity = selectedTestsMap.get(test.getId());
            ManageMedicalTestListResponseDTO responseDTO = new ManageMedicalTestListResponseDTO();
            responseDTO.setId(test.getId());
            responseDTO.setTestName(test.getTestName());
            responseDTO.setCategory(test.getCategory().getCategoryName());
            responseDTO.setDepartment(test.getDepartment().getDepartmentName());
            responseDTO.setSelected(vendorMedicalTestEntity != null);
            responseDTO.setTestPrice(vendorMedicalTestEntity !=null ? vendorMedicalTestEntity.getMedicalTestPrice() : null);
            return  responseDTO;
        }).getContent();
        return new PageResponseDTO<>(response,page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages(),page.isLast());
    }

    @Override
    public String updateMedicalTests(List<ManageMedicalTestCreateRequestDTO> tests, UserAuthEntity userAuthEntity) {
        VendorEntity vendor = vendorRepository.findById(userAuthEntity.getVendorID().getId()).orElseThrow(() ->
                    new VendorException("id",String.format("Invalid Vendor with ID : %d",userAuthEntity.getVendorID().getId())));

        Set<Long> newTestIds = tests.stream()
                    .map(ManageMedicalTestCreateRequestDTO::getTestID)
                    .collect(Collectors.toSet());

        List<VendorMedicalTestEntity> existingTests =
                    vendorMedicalTestRepository.findByVendorId(vendor.getId());

        Map<Long, VendorMedicalTestEntity> existingMap =
                    existingTests.stream()
                            .collect(Collectors.toMap(
                                    e -> e.getMedicalTest().getId(),
                                    e -> e
                            ));

        List<VendorMedicalTestEntity> toDelete = existingTests.stream()
                    .filter(e -> !newTestIds.contains(e.getMedicalTest().getId()))
                    .toList();

        vendorMedicalTestRepository.deleteAll(toDelete);

        List<MedicalTestEntity> medicalTests =
                    medicalTestRepository.findAllById(newTestIds);

        Map<Long, MedicalTestEntity> medicalTestMap =
                    medicalTests.stream()
                            .collect(Collectors.toMap(MedicalTestEntity::getId, m -> m));

            List<VendorMedicalTestEntity> result = new ArrayList<>();

            for (ManageMedicalTestCreateRequestDTO dto : tests) {

                MedicalTestEntity medicalTest = medicalTestMap.get(dto.getTestID());

                if (medicalTest == null) {
                    throw new MedicalTestException(
                            "id",
                            "Invalid Medical Test with ID : " + dto.getTestID()
                    );
                }

                VendorMedicalTestEntity entity = existingMap.get(dto.getTestID());

                if (entity == null) {
                    entity = new VendorMedicalTestEntity();
                    entity.setVendor(vendor);
                    entity.setMedicalTest(medicalTest);
                }

                entity.setMedicalTestPrice(dto.getTestPrice());
                result.add(entity);
            }

            vendorMedicalTestRepository.saveAll(result);
            return "Tests Details Updated Successfully.";
    }
}
