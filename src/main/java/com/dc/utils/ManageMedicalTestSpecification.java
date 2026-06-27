package com.dc.utils;

import com.dc.entity.*;
        import jakarta.persistence.criteria.*;
        import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.Objects;

public class ManageMedicalTestSpecification {
    public static Specification<MedicalTestEntity> getMedicalTests(String testName,String category, String department){
        return new Specification<MedicalTestEntity>() {

            @Override
            public Predicate toPredicate(Root<MedicalTestEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if(testName != null && !testName.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("testName")), "%"+testName.toLowerCase()+"%"));
                }
                else if(category != null && !category.isEmpty()){
                    Join<VendorMedicalTestEntity, MedicalTestCategoryEntity> categoryJoin = root.join("category");
                    predicate = cb.and(predicate, cb.equal(categoryJoin.get("id"), category));
                }
                else if(department != null && !department.isEmpty()){
                    Join<VendorMedicalTestEntity, MedicalTestDepartmentEntity> departmentJoin = root.join("department");
                    predicate = cb.and(predicate, cb.equal(departmentJoin.get("id"), department));
                }

                predicate = cb.and(predicate, cb.isTrue(root.get("active")));
                return predicate;
            }
        };
    }
}
