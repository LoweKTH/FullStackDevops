package com.fullstackdevops.patientms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoteDto {
    private Long id;
    private String content;
    private Date createdAt;
    private Long doctorId;
    private Long staffId;
}
