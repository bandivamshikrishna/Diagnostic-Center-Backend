package com.dc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_patient_visit_details")
@Audited
@Getter
@Setter
public class PatientVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String visitCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",referencedColumnName = "id", nullable = false)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorEntity doctor;

    @Column(nullable = false)
    private Date sampleCollectedDate;

    @Column(nullable = false)
    private LocalTime sampleCollectedTime;

    @Column(nullable = false)
    private Date reportedDate;

    @Column(nullable = false)
    private LocalTime reportedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientVisit")
    private List<PatientMedicalTestEntity> medicalTests;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientVisit")
    private List<PatientMedicalTestPackageEntity> testPackages;
}
