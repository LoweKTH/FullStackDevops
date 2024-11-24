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
        dto.setDoctorstaffId(diagnosis.getDoctorstaffId());
        return dto;
    }

    public static Diagnosis toEntity(DiagnosisDto dto, Patient patient) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(dto.getDiagnosisName());
        diagnosis.setDescription(dto.getDescription());
        diagnosis.setPatientId(patient.getUserId());
        diagnosis.setDoctorstaffId(dto.getDoctorstaffId());
        return diagnosis;
    }
}
