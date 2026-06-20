package com.dc.repository;

import com.dc.entity.VendorBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorBranchRepository extends JpaRepository<VendorBranchEntity, Long> {
    Optional<VendorBranchEntity> findByBranchCodeAndVendor_VendorCode(String branchCode,String vendor);
}
