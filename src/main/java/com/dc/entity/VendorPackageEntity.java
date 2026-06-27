package com.dc.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.List;

@Setter
@Getter
@Audited
@Entity
@Table(name = "tbl_vendor_package_details")
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

    @Column(nullable = false)
    private Double packagePrice;

}
