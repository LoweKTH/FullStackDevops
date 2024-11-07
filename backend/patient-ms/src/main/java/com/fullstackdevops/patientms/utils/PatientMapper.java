package com.fullstackdevops.patientms.utils;

import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.model.Patient;

public class PatientMapper {

    public static PatientDto toDto(Patient patient) {
        if(patient == null) return null;
        PatientDto dto = new PatientDto();
        dto.setId(patient.getId());
        dto.setFirstname(patient.getFirstname());
        dto.setLastname(patient.getLastname());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setAddress(patient.getAddress());
        dto.setGender(patient.getGender());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNumber(patient.getPhoneNumber());

        return dto;
    }

    public static Patient toEntity(PatientDto dto) {
        if(dto == null) return null;

        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setFirstname(dto.getFirstname());
        patient.setLastname(dto.getLastname());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setAddress(dto.getAddress());
        patient.setGender(dto.getGender());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());

        return patient;
    }
}
