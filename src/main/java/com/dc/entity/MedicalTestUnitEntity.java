package com.dc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@Entity
@Table(name = "tbl_medical_test_units")
@Getter
@Setter
public class MedicalTestUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String unit;

    private String unitDesc;

    @Column(nullable = false,columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id",referencedColumnName = "id", nullable = false)
    private UserAuthEntity createdBy;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_user_id",referencedColumnName = "id")
    private UserAuthEntity lastModifiedByUserID;

    private LocalDateTime lastModifiedDate;
}
