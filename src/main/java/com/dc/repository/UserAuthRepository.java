package com.dc.repository;

import com.dc.entity.UserAuthEntity;
import com.dc.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity,Long> {
     Boolean existsByEmail(String email);
     Optional<UserAuthEntity> findByEmail(String email);
     Boolean existsByRole_RoleCode(String roleCode);

     @Query(value = "select nextVal('user_code_seq')", nativeQuery = true)
     Long getNextUserCode();
}
