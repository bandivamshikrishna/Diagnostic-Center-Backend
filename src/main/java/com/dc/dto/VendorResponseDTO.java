package com.dc.dto;

import java.util.List;

public class VendorResponseDTO {

    private Long ID;
    private String vendorCode;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String logoFolderPath;
    private String CreatedBy;
    private String CreatedDate;
    private Boolean active;
    private Long maxNoOfUsers;
    private String activationEndDate;
    private List<VendorBranchDTO> branches;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoFolderPath() {
        return logoFolderPath;
    }

    public void setLogoFolderPath(String logoFolderPath) {
        this.logoFolderPath = logoFolderPath;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getMaxNoOfUsers() {
        return maxNoOfUsers;
    }

    public void setMaxNoOfUsers(Long maxNoOfUsers) {
        this.maxNoOfUsers = maxNoOfUsers;
    }

    public List<VendorBranchDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<VendorBranchDTO> branches) {
        this.branches = branches;
    }

    public String getActivationEndDate() {
        return activationEndDate;
    }

    public void setActivationEndDate(String activationEnDate) {
        this.activationEndDate = activationEnDate;
    }
}
