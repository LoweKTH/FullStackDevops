package com.fullstackdevops.patientms.dto;

import lombok.*;

import java.util.Date;

@Data
public class DiagnosisDto {
    private Long id;
    private String diagnosisName;
    private String description;
    private Date diagnosisDate;
    private String patientId;
    private String doctorstaffId;
    private String doctorstaffName;
    private String role;
}
