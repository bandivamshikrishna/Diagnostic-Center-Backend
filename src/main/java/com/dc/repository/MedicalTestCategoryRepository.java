package com.dc.repository;

import com.dc.entity.MedicalTestCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestCategoryRepository extends JpaRepository<MedicalTestCategoryEntity,Long> {
}
