package com.dc.repository;

import com.dc.entity.PatientGenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientGenderRepository extends JpaRepository<PatientGenderEntity,Long> {
    List<PatientGenderEntity> findAllByOrderBySequenceAsc();
    Optional<PatientGenderEntity> findByGenderCode(String genderCode);
}
