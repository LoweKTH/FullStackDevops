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
    private Long doctorStaffId;
    @JsonProperty("patientId")
    private Long patientId;


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

    public Long getDoctorStaffId() {
        return doctorStaffId;
    }

    public void setDoctorStaffId(Long doctorStaffId) {
        this.doctorStaffId = doctorStaffId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
