package com.dc.entity;

import com.dc.enums.PatientStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Audited
@Getter
@Setter
@Entity
@Table(name = "tbl_patient_details")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String patientUserCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id", referencedColumnName = "id")
    private PatientTitleEntity patientTitle;

    @Column(nullable = false)
    private String name;

    @NotNull
    private Date dateOfBirth;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private PatientGenderEntity gender;

    private String phoneNumber;

    @Email
    private String email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorEntity doctor;


    private Date sampleCollectedDate;

    private LocalTime sampleCollectedTime;

    private Date reportedDate;

    private LocalTime reportedTime;



    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id",nullable = false)
    private UserAuthEntity createdBy;


    private LocalDateTime lastModifiedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_user_id", referencedColumnName = "id")
    private UserAuthEntity lastModifiedBy;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id",referencedColumnName = "id",nullable = false)
    private VendorEntity vendorID;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private List<PatientMedicalTestEntity> medicalTests;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private List<PatientMedicalTestPackageEntity> testPackages;

}
