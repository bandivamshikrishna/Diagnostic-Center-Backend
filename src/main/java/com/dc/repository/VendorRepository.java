package com.dc.repository;

import com.dc.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    VendorEntity findByEmail(String email);
    Boolean existsByEmailAndIdNot(String email, Long id);
    Boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    Optional<VendorEntity> findByVendorCode(String vendorCode);
    @Query(value = "select nextVal('vendor_code_seq')", nativeQuery = true)
     Long getNextVendorCode();
}
