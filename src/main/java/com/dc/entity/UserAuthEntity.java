package com.dc.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Audited
@Entity
@Table(name = "tbl_user_details")
public class UserAuthEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    private String userCode;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private UserRoleEntity role;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorEntity vendorID;

    @ManyToOne
    @JoinColumn(name = "vendor_branch", referencedColumnName = "id")
    private VendorBranchEntity vendorBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id",referencedColumnName = "id")
    private UserAuthEntity createdByUserID;

    @Column(nullable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_user_id",referencedColumnName = "id")
    private UserAuthEntity lastModifiedByUserID;

    private LocalDate lastModifiedDate;

    @Column(name = "is_active",nullable = false)
    private Boolean active;

    @Column(name = "is_locked",nullable = false)
    private Boolean locked;

    @Column
    private LocalDateTime lastLoginDateTime;

    @Column
    private LocalDateTime lastLogoutDateTime;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserAuthTokenEntity> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_"+getRole().getRoleName();
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoleEntity getRole() {
        return role;
    }

    public void setRole(UserRoleEntity userRole){
        this.role = userRole;
    }

    public VendorEntity getVendorID() {
        return vendorID;
    }

    public void setVendorID(VendorEntity vendorID) {
        this.vendorID = vendorID;
    }

    public UserAuthEntity getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCreatedByUserID(UserAuthEntity createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public LocalDateTime getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(LocalDateTime lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public LocalDateTime getLastLogoutDateTime() {
        return lastLogoutDateTime;
    }

    public void setLastLogoutDateTime(LocalDateTime lastLogoutDateTime) {
        this.lastLogoutDateTime = lastLogoutDateTime;
    }

    public VendorBranchEntity getVendorBranch() {
        return vendorBranch;
    }

    public void setVendorBranch(VendorBranchEntity vendorBranch) {
        this.vendorBranch = vendorBranch;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public UserAuthEntity getLastModifiedByUserID() {
        return lastModifiedByUserID;
    }

    public void setLastModifiedByUserID(UserAuthEntity lastModifiedByUserID) {
        this.lastModifiedByUserID = lastModifiedByUserID;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
