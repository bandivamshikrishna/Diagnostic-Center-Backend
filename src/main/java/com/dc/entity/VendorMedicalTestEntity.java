package com.dc.entity;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Audited
@Entity(name = "tbl_vendor_medical_test_details")
public class VendorMedicalTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_test_id", referencedColumnName = "id", nullable = false)
    private MedicalTestEntity medicalTest;

    @ManyToOne()
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", nullable = false)
    private VendorEntity vendor;


    @Column(nullable = false)
    private Double medicalTestPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalTestEntity getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(MedicalTestEntity medicalTest) {
        this.medicalTest = medicalTest;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public Double getMedicalTestPrice() {
        return medicalTestPrice;
    }

    public void setMedicalTestPrice(Double medicalTestPrice) {
        this.medicalTestPrice = medicalTestPrice;
    }
}
