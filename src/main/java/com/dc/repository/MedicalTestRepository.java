package com.dc.repository;

import com.dc.entity.MedicalTestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestRepository extends JpaRepository<MedicalTestEntity, Long> , JpaSpecificationExecutor<MedicalTestEntity> {
    Boolean existsByTestNameIgnoreCase(String testName);
    Boolean existsByTestNameIgnoreCaseAndIdNot(String testName,Long id);
    Boolean existsByTestCode(String testCode);

    @Query(value = "select nextVal('test_code_seq')", nativeQuery = true)
    Long getNextTestCode();



    @Override
    @EntityGraph(attributePaths = {
            "department",
            "category",
            "specimen",
            "method",
            "unit",
            "createdByUserID",
            "lastModifiedByUserID"
    })
    Page<MedicalTestEntity> findAll(Specification<MedicalTestEntity> spec, Pageable pageable);
}
