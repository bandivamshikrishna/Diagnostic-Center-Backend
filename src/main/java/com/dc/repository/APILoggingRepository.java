package com.dc.repository;

import com.dc.entity.APILoggingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APILoggingRepository extends JpaRepository<APILoggingEntity, Long> {
    Boolean existsByUuid(String uuid);
    APILoggingEntity findByUuid(String uuid);
}
