package com.dc.entity;


import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Audited
@Entity(name = "tbl_patient_medical_test_details")
public class PatientMedicalTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_test_id", referencedColumnName = "id", nullable = false)
    private VendorMedicalTestEntity medicalTest;

    private Double testDiscount;

    public Double getTestDiscount() {
        return testDiscount;
    }

    public void setTestDiscount(Double testDiscount) {
        this.testDiscount = testDiscount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public VendorMedicalTestEntity getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(VendorMedicalTestEntity medicalTest) {
        this.medicalTest = medicalTest;
    }
}
