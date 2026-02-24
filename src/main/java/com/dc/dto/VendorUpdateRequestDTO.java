package com.dc.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Date;

public class VendorUpdateRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid Email ID")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone Number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Phone Number"
    )
    private String phoneNumber;

    @NotNull(message = "Active Flag is required")
    private Boolean active;

    @NotNull(message = "Activation End Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate activationEndDate;

    @NotNull(message = "Last Modified By User ID is required")
    private Long lastModifiedByUserID;

    @NotNull(message = "Max No Of Users is required")
    private Long maxNoOfUsers;

    public Long getLastModifiedByUserID() {
        return lastModifiedByUserID;
    }

    public void setLastModifiedByUserID(Long lastModifiedByUserID) {
        this.lastModifiedByUserID = lastModifiedByUserID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getActivationEndDate() {
        return activationEndDate;
    }

    public void setActivationEndDate(LocalDate activationEndDate) {
        this.activationEndDate = activationEndDate;
    }

    public Long getMaxNoOfUsers() {
        return maxNoOfUsers;
    }

    public void setMaxNoOfUsers(Long maxNoOfUsers) {
        this.maxNoOfUsers = maxNoOfUsers;
    }
}

