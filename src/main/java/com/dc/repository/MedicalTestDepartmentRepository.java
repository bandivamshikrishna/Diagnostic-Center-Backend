package com.dc.repository;

import com.dc.entity.MedicalTestDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestDepartmentRepository extends JpaRepository<MedicalTestDepartmentEntity,Long> {

}
