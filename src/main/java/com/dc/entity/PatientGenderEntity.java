package com.dc.entity;


import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Audited
@Entity(name = "tbl_gender_details")
public class PatientGenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(nullable = false,unique = true)
    private String gender;

    @Column(nullable = false,name = "is_active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
