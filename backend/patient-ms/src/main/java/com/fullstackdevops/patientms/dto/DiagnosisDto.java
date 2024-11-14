package com.fullstackdevops.patientms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DiagnosisDto {
    private Long id;
    private String diagnosisName;
    private String description;
    private Date diagnosisDate;
    private Long patientId;
    private Long doctorId;
}
