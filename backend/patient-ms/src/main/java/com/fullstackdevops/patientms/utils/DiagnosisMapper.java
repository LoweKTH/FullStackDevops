package com.fullstackdevops.patientms.utils;

import com.fullstackdevops.patientms.dto.DiagnosisDto;
import com.fullstackdevops.patientms.model.Diagnosis;
import com.fullstackdevops.patientms.model.Patient;

public class DiagnosisMapper {
    public static DiagnosisDto toDto(Diagnosis diagnosis) {
        DiagnosisDto dto = new DiagnosisDto();
        dto.setId(diagnosis.getId());
        dto.setDiagnosisName(diagnosis.getName());
        dto.setDescription(diagnosis.getDescription());
        dto.setDiagnosisDate(diagnosis.getDiagnosisDate());
        dto.setPatientId(diagnosis.getPatient().getId());
        dto.setDoctorId(diagnosis.getDoctorId());
        return dto;
    }

    public static Diagnosis toEntity(DiagnosisDto dto, Patient patient) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(dto.getDiagnosisName());
        diagnosis.setDescription(dto.getDescription());
        diagnosis.setPatient(patient);
        diagnosis.setDoctorId(dto.getDoctorId());
        return diagnosis;
    }
}
