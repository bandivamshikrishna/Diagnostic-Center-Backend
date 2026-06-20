package com.dc.repository;

import com.dc.entity.MedicalTestMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestMethodRepository extends JpaRepository<MedicalTestMethodEntity,Long> {
}
