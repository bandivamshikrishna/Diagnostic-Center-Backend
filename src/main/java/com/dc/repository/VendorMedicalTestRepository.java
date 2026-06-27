package com.dc.repository;

import com.dc.entity.VendorMedicalTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VendorMedicalTestRepository extends JpaRepository<VendorMedicalTestEntity, Long>, JpaSpecificationExecutor<VendorMedicalTestEntity> {
    Optional<VendorMedicalTestEntity> findByVendorIdAndMedicalTestId(Long vendorId,Long medicalTestId);
    List<VendorMedicalTestEntity> findByVendorId(Long vendorId);
    @Query("""
    SELECT v FROM VendorMedicalTestEntity v WHERE v.vendor.id = :vendorId AND v.medicalTest.id IN :testIds""")
    List<VendorMedicalTestEntity> findByVendorIdAndMedicalTestIds(@Param("vendorId") Long vendorId,
                                                                  @Param("testIds") List<Long> testIds);
}
