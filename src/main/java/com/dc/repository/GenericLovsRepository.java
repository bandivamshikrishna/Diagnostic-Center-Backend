package com.dc.repository;

import com.dc.entity.GenericLovsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenericLovsRepository extends JpaRepository<GenericLovsEntity,Long> {
    List<GenericLovsEntity> findByTypeOrderBySequenceAsc(String type);
}
