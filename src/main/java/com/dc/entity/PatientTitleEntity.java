package com.dc.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Getter
@Setter
@Table(name = "tbl_title_details")
public class PatientTitleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private Long sequence;

    @Column(nullable = false,unique = true)
    private String titleCode;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false,name = "is_active")
    private Boolean active;

}
