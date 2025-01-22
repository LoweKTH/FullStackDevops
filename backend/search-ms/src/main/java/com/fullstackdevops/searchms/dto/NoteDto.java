package com.fullstackdevops.searchms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Date;


@RegisterForReflection
public class NoteDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("createdAt")
    private Date createdAt;
    @JsonProperty("doctorStaffId")
    private String doctorStaffId;
    @JsonProperty("patientId")
    private String patientId;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDoctorStaffId() {
        return doctorStaffId;
    }

    public void setDoctorStaffId(String doctorStaffId) {
        this.doctorStaffId = doctorStaffId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
