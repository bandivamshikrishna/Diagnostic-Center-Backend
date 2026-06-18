package com.dc.repository;

import com.dc.entity.MedicalTestCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalTestCategoryRepository extends JpaRepository<MedicalTestCategoryEntity,Long> {
}
