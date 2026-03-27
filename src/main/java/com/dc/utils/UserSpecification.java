package com.dc.utils;

import com.dc.entity.UserAuthEntity;
import com.dc.entity.UserRoleEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.Objects;

public class UserSpecification {
    public static Specification<UserAuthEntity> getUserFilters(String userCode, String name,
                                                               String email, String role, Date startDate,Date endDate,String filterType){
        return new Specification<UserAuthEntity>() {
            @Override
            public Predicate toPredicate(Root<UserAuthEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if(userCode!=null && !userCode.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("userCode")), "%"+userCode.toLowerCase()+"%"));
                }
                else if(name != null && !name.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("fullName")), "%"+name.toLowerCase()+"%"));
                }
                else if(email != null && !email.isEmpty()){
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%"+email.toLowerCase()+"%"));
                }
                else if(role != null && !role.isEmpty()){
                    Join<UserAuthEntity, UserRoleEntity> roleJoin = root.join("role");
                    predicate = cb.and(predicate, cb.equal(roleJoin.get("roleCode"), role));
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
