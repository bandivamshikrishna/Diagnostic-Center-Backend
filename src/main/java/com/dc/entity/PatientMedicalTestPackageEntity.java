package com.dc.entity;


import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Audited
@Entity(name = "tbl_patient_package_details")
public class PatientMedicalTestPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "id", nullable = false)
    private VendorPackageEntity packageDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private PatientEntity patient;

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    private Double packageDiscount;

    public Double getPackageDiscount() {
        return packageDiscount;
    }

    public void setPackageDiscount(Double packageDiscount) {
        this.packageDiscount = packageDiscount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VendorPackageEntity getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(VendorPackageEntity packageDetails) {
        this.packageDetails = packageDetails;
    }
}
