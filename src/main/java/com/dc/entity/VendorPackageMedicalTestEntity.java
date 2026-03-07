package com.dc.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Audited
@Entity(name = "tbl_vendor_package_medical_test_details")
public class VendorPackageMedicalTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "package_id",referencedColumnName = "id",nullable = false)
    private VendorPackageEntity packageID;

    @ManyToOne()
    @JoinColumn(name = "medical_test_id", referencedColumnName = "id", nullable = false)
    private MedicalTestEntity medicalTest;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VendorPackageEntity getPackageID() {
        return packageID;
    }

    public void setPackageID(VendorPackageEntity packageID) {
        this.packageID = packageID;
    }

    public MedicalTestEntity getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(MedicalTestEntity medicalTest) {
        this.medicalTest = medicalTest;
    }
}
