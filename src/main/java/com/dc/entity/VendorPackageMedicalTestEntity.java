package com.dc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Setter
@Getter
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


}
