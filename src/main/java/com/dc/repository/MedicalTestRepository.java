package com.dc.repository;

import com.dc.entity.MedicalTestEntity;
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
}
