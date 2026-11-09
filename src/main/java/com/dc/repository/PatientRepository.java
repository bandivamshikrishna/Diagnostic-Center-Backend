package com.dc.repository;

import com.dc.entity.MedicalTestEntity;
import com.dc.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>, JpaSpecificationExecutor<PatientEntity>  {
    Boolean existsByPhoneNumberAndDateOfBirth(String phoneNumber, Date dateOfBirth);
    Boolean existsByEmailIgnoreCaseAndDateOfBirth(String email, Date dateOfBirth);

    @Query(value = "select nextVal('patient_code_seq')", nativeQuery = true)
    Long getNextPatientUserCode();
    Optional<PatientEntity> findByPatientUserCode(String patientCode);

    @Override
    @EntityGraph(attributePaths = {
            "patientUserCode",
            "name",
            "phoneNumber",
            "email"
    })
    Page<PatientEntity> findAll(Specification<PatientEntity> spec, Pageable pageable);
}
