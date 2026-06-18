package com.dc.utils;

import com.dc.entity.MedicalTestEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.Objects;

public class MedicalTestSpecification {
    public static Specification<MedicalTestEntity> getMedicalTestsFilters(String testCode, String testName,
                                                                          String category, String department, Date startDate, Date endDate, String filterType){
        return new Specification<MedicalTestEntity>() {

            @Override
            public Predicate toPredicate(Root<MedicalTestEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if(testCode!=null && !testCode.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("testCode")), "%"+testCode.toLowerCase()+"%"));
                }
                else if(testName != null && !testName.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("testName")), "%"+testName.toLowerCase()+"%"));
                }
                else if(category != null && !category.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("category")), "%"+category.toLowerCase()+"%"));
                }
                else if(department != null && !department.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("department")), "%"+department.toLowerCase()+"%"));
                }
                else if(startDate!=null && endDate!=null){
                    predicate = cb.and(predicate, cb.and(cb.greaterThanOrEqualTo(root.get("createdDate"), startDate),
                            cb.lessThanOrEqualTo(root.get("createdDate"), endDate)));
                }

               if(Objects.equals(filterType, "1"))
                   predicate = cb.and(predicate, cb.isTrue(root.get("active")));
               else if(Objects.equals(filterType, "0"))
                   predicate = cb.and(predicate, cb.isFalse(root.get("active")));


                return predicate;
            }
        };
    }
}
