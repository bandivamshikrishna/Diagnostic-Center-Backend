package com.dc.dto;

public class UserResponseDTO {
    private String email;
    private String role;
    private String vendor;
    private String userCode;
    private Long id;
    private String fullName;
    private String vendorBranch;
    private String roleFullName;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getVendorBranch() {
        return vendorBranch;
    }

    public void setVendorBranch(String vendorBranch) {
        this.vendorBranch = vendorBranch;
    }

    public String getRoleFullName() {
        return roleFullName;
    }

    public void setRoleFullName(String roleFullName) {
        this.roleFullName = roleFullName;
    }
}
