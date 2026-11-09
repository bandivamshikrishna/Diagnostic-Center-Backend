package com.dc.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Getter
@Setter
@Table(name = "tbl_patient_medical_test_details")
public class PatientMedicalTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_visit_id", referencedColumnName = "id", nullable = false)
    private PatientVisitEntity patientVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_test_id", referencedColumnName = "id", nullable = false)
    private VendorMedicalTestEntity medicalTest;

    private Double testDiscount;


}
