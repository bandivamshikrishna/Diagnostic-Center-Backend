package com.dc.repository;

import com.dc.entity.MedicalTestSpecimenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestSpecimenRepository extends JpaRepository<MedicalTestSpecimenEntity,Long> {
}
