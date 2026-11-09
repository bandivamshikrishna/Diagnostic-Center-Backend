package com.dc.utils;

import com.dc.entity.PatientEntity;
import com.dc.entity.PatientVisitEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PatientSpecification {
    public static Specification<PatientEntity> getPatients(
            String email, Date visitDate
            ){

        return new Specification<PatientEntity>() {
            @Override
            public Predicate toPredicate(Root<PatientEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if(email != null && !email.isEmpty()){
                    System.out.println("Query--------------------------------------");
                    predicate = cb.or(predicate, cb.like(cb.lower(root.get("patientUserCode")),"%"+email.toLowerCase()+"%"));
                    predicate = cb.or(predicate, cb.like(cb.lower(root.get("name")),"%"+email.toLowerCase()+"%"));
                    predicate = cb.or(predicate, cb.like(cb.lower(root.get("phoneNumber")),"%"+email.toLowerCase()+"%"));
                    predicate = cb.or(predicate, cb.like(cb.lower(root.get("email")),"%"+email.toLowerCase()+"%"));
                }
                if(visitDate != null){
                    Join<PatientEntity, PatientVisitEntity> visitJoin = root.join("patientVisitEntities");
                    predicate = cb.and(predicate, cb.equal(visitJoin.get("sampleCollectedDate"),visitDate));
                }
                return predicate;
            }
        };
    }

}
