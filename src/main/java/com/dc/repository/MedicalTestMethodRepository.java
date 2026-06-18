package com.dc.repository;

import com.dc.entity.MedicalTestMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalTestMethodRepository extends JpaRepository<MedicalTestMethodEntity,Long> {
}
