package com.fullstackdevops.searchms.dto;

import java.util.Date;

public class NoteDto {
    private Long id;
    private String content;
    private Date createdAt;
    private Long doctorStaffId;
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
