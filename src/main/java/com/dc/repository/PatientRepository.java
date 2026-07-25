package com.dc.repository;

import com.dc.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    Boolean existsByPhoneNumberAndDateOfBirth(String phoneNumber, Date dateOfBirth);
    Boolean existsByEmailIgnoreCaseAndDateOfBirth(String email, Date dateOfBirth);

    @Query(value = "select nextVal('patient_code_seq')", nativeQuery = true)
    Long getNextPatientUserCode();
}
