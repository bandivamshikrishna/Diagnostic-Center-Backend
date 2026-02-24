package com.dc.repository;

import com.dc.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    public Boolean existsByEmail(String email);
    public Boolean existsByPhoneNumber(String phoneNumber);
    public VendorEntity findByEmail(String email);
    public Boolean existsByEmailAndIdNot(String email, Long id);
    public Boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    @Query(value = "select nextVal('vendor_code_seq')", nativeQuery = true)
    public Long getNextVendorCode();
}
