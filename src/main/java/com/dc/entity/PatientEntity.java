package com.dc.entity;

import com.dc.enums.PatientStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Audited
@Entity
@Table(name = "tbl_patient_details")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PatientStatusEnum patientStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id",referencedColumnName = "id",nullable = false)
    private VendorEntity vendorID;




    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PatientMedicalTestEntity> medicalTests;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientMedicalTestPackageEntity> testPackages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientTitleEntity getPatientTitle() {
        return patientTitle;
    }

    public void setPatientTitle(PatientTitleEntity patientTitle) {
        this.patientTitle = patientTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PatientGenderEntity getGender() {
        return gender;
    }

    public void setGender(PatientGenderEntity gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public Date getSampleCollectedDate() {
        return sampleCollectedDate;
    }

    public void setSampleCollectedDate(Date sampleCollectedDate) {
        this.sampleCollectedDate = sampleCollectedDate;
    }

    public LocalTime getSampleCollectedTime() {
        return sampleCollectedTime;
    }

    public void setSampleCollectedTime(LocalTime sampleCollectedTime) {
        this.sampleCollectedTime = sampleCollectedTime;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public LocalTime getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(LocalTime reportedTime) {
        this.reportedTime = reportedTime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserAuthEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserAuthEntity createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public UserAuthEntity getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(UserAuthEntity lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    public PatientStatusEnum getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatusEnum patientStatus) {
        this.patientStatus = patientStatus;
    }

    public VendorEntity getVendorID() {
        return vendorID;
    }

    public void setVendorID(VendorEntity vendorID) {
        this.vendorID = vendorID;
    }

    public List<PatientMedicalTestEntity> getMedicalTests() {
        return medicalTests;
    }

    public void setMedicalTests(List<PatientMedicalTestEntity> medicalTests) {
        this.medicalTests = medicalTests;
    }

    public List<PatientMedicalTestPackageEntity> getPackages() {
        return testPackages;
    }

    public void setPackages(List<PatientMedicalTestPackageEntity> testPackages) {
        this.testPackages = testPackages;
    }
}
