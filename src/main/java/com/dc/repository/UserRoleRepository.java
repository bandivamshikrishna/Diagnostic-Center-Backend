package com.dc.repository;

import com.dc.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByRoleCode(String roleCode);
    Boolean existsByRoleCode(String roleCode);
    List<UserRoleEntity> findByRoleCodeNot(String roleCode);
}
