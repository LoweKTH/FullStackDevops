package com.fullstackdevops.patientms.utils;

import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {

    public static PatientDto toDto(Patient patient) {
        if(patient == null) return null;
        PatientDto dto = new PatientDto();
        dto.setSocialSecurityNumber(patient.getSocialSecurityNumber());
        dto.setUserId(patient.getUserId());
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
        patient.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        patient.setUserId(dto.getUserId());
        patient.setFirstname(dto.getFirstname());
        patient.setLastname(dto.getLastname());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setAddress(dto.getAddress());
        patient.setGender(dto.getGender());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());

        return patient;
    }

    public static List<PatientDto> toDtoList(List<Patient> patients) {
        return patients.stream()
                .map(PatientMapper::toDto)
                .collect(Collectors.toList());
    }
}
