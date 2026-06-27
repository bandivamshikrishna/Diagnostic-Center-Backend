package com.dc.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name = "tbl_vendor_medical_test_details")
@Getter
@Setter
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
}
