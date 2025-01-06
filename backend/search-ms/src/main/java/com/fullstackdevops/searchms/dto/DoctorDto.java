package com.fullstackdevops.searchms.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public class DoctorDto {

    @JsonbProperty("socialSecurityNumber")
    private Long socialSecurityNumber;
    @JsonbProperty("userId")
    private Long userId;
    @JsonbProperty("firstname")
    private String firstname;
    @JsonbProperty("lastname")
    private String lastname;
    @JsonbProperty("email")
    private String email;
    @JsonbProperty("phoneNumber")
    private String phoneNumber;
    @JsonbProperty("specialty")
    private String specialty;

    public DoctorDto(Long socialSecurityNumber, Long userId, String firstname, String lastname, String email, String phoneNumber, String specialty) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialty = specialty;
    }



    public Long getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(Long socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
