package com.fullstackdevops.searchms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class DoctorWithPatients {

    @JsonProperty
    private DoctorDto doctor;

    @JsonProperty
    private List<PatientDto> patients;

    public DoctorWithPatients(DoctorDto doctor, List<PatientDto> patients) {
        this.doctor = doctor;
        this.patients = patients;
    }
    public DoctorWithPatients(){

    }

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
