package com.fullstackdevops.searchms.dto;

import jakarta.json.bind.annotation.JsonbProperty;

import java.util.Date;
import java.util.List;

public class PatientDto {
    @JsonbProperty("id")
    private Long id; // Patient ID
    @JsonbProperty("socialSecurityNumber")
    private Long socialSecurityNumber;
    @JsonbProperty("userId")
    private Long userId; // User ID associated with the patient
    @JsonbProperty("firstname")
    private String firstname;
    @JsonbProperty("lastname")
    private String lastname;
    @JsonbProperty("dateOfBirth")
    private Date dateOfBirth;
    @JsonbProperty("address")
    private String address;
    @JsonbProperty("gender")
    private String gender;
    @JsonbProperty("email")
    private String email;
    @JsonbProperty("phoneNumber")
    private String phoneNumber;

    @JsonbProperty("notes")
    private List<NoteDto> notes; // Notes associated with the patient
    @JsonbProperty("diagnoses")
    private List<DiagnosisDto> diagnoses; // Diagnoses associated with the patient

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public List<NoteDto> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDto> notes) {
        this.notes = notes;
    }

    public List<DiagnosisDto> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<DiagnosisDto> diagnoses) {
        this.diagnoses = diagnoses;
    }
}
