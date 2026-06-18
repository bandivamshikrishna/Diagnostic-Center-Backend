package com.dc.repository;

import com.dc.entity.UserAuthEntity;
import com.dc.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity,Long>, JpaSpecificationExecutor<UserAuthEntity> {
     Boolean existsByEmail(String email);
     Optional<UserAuthEntity> findByEmail(String email);
     Boolean existsByRole_RoleCode(String roleCode);
     List<UserAuthEntity> findAllByVendorID_VendorCodeAndVendorBranch_BranchCode(String vendorCode, String vendorBranchCode);

     @Query(value = "select nextVal('user_code_seq')", nativeQuery = true)
     Long getNextUserCode();

     Boolean existsByEmailAndIdNot(String email, Long id);
}
