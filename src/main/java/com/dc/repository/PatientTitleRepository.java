package com.dc.repository;

import com.dc.entity.PatientTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientTitleRepository extends JpaRepository<PatientTitleEntity,Long> {
    List<PatientTitleEntity> findAllByOrderBySequenceAsc();
    Optional<PatientTitleEntity> findByTitleCode(String titleCode);
}
