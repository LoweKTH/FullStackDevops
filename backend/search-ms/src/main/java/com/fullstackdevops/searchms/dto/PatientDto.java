package com.fullstackdevops.searchms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.Date;
import java.util.List;

@RegisterForReflection
public class PatientDto {
    @JsonProperty("id")
    private Long id; // Patient ID
    @JsonProperty("socialSecurityNumber")
    private Long socialSecurityNumber;
    @JsonProperty("userId")
    private String userId; // User ID associated with the patient
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;
    @JsonProperty("address")
    private String address;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("notes")
    private List<NoteDto> notes; // Notes associated with the patient
    @JsonProperty("diagnoses")
    private List<DiagnosisDto> diagnoses; // Diagnoses associated with the patient


    public PatientDto() {}
    public PatientDto(Long id, Long socialSecurityNumber, String userId, String firstname, String lastname, Date dateOfBirth, String address, String gender, String email, String phoneNumber, List<NoteDto> notes, List<DiagnosisDto> diagnoses) {
        this.id = id;
        this.socialSecurityNumber = socialSecurityNumber;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.diagnoses = diagnoses;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    @Override
    public String toString() {
        return "{" +
                "socialSecurityNumber=" + socialSecurityNumber +
                ", userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + " " +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}