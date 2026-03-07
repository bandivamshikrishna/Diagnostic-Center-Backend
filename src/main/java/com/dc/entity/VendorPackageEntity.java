package com.dc.entity;


import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.util.List;

@Audited
@Entity(name = "tbl_vendor_package_details")
public class VendorPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String packageName;

    @ManyToOne()
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", nullable = false)
    private VendorEntity vendor;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "packageID",cascade = CascadeType.ALL)
    private List<VendorPackageMedicalTestEntity> medicalTest;

    public List<VendorPackageMedicalTestEntity> getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(List<VendorPackageMedicalTestEntity> medicalTest) {
        this.medicalTest = medicalTest;
    }

    @Column(nullable = false)
    private Double packagePrice;

    public Double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }
}
