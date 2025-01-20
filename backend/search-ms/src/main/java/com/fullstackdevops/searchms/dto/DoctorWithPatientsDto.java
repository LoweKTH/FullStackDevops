package com.fullstackdevops.searchms.dto;

import java.util.List;

public class DoctorWithPatients {
    private DoctorDto doctor;
    private List<PatientDto> patients;

    // Getters and setters
    public DoctorDto getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDto doctor) {
        this.doctor = doctor;
    }

    public List<PatientDto> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDto> patients) {
        this.patients = patients;
    }
}
