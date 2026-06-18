package com.dc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCreateOrUpdateRequestDTO {

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @Email
    @NotBlank(message = "Email is required")
    public String email;

    @NotNull(message = "Role is required")
    private String role;

    @NotNull(message = "Vendor is required")
    private String  vendor;

   @NotNull(message = "Vendor Branch is required")
   private String vendorBranch;


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

    public String getVendorBranch() {
        return vendorBranch;
    }

    public void setVendorBranch(String vendorBranch) {
        this.vendorBranch = vendorBranch;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
