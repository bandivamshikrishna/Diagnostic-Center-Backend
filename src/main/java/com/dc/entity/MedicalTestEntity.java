package com.dc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
@Audited
@Entity(name = "tbl_medical_test_details")
@Getter
@Setter
public class MedicalTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private MedicalTestDepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private MedicalTestCategoryEntity category;

    private String panelName;

    private Boolean isPanel;

    @Column(nullable = false,unique = true)
    private String testName;

    @Column(nullable = false, unique = true)
    private String testCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specimen_id", referencedColumnName = "id", nullable = false)
    private MedicalTestSpecimenEntity specimen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id", referencedColumnName = "id", nullable = false)
    private MedicalTestMethodEntity method;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String normalRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id", nullable = false)
    private MedicalTestUnitEntity unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id",referencedColumnName = "id", nullable = false)
    private UserAuthEntity createdByUserID;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_user_id", referencedColumnName = "id")
    private UserAuthEntity lastModifiedByUserID;

    @Column(nullable = true)
    private LocalDateTime lastModifiedDate;



    public Boolean getPanel() {
        return isPanel;
    }

    public void setPanel(Boolean panel) {
        isPanel = panel;
    }
}
