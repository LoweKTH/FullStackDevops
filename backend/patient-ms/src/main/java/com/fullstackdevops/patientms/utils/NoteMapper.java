package com.fullstackdevops.patientms.utils;

import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.model.Note;
import com.fullstackdevops.patientms.model.Patient;

public class NoteMapper {

    public static NoteDto toDto(Note note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setDoctorstaffId(note.getDoctorstaffId());
        return dto;
    }

    public static Note toEntity(NoteDto noteDto, Patient patient) {
        Note note = new Note();
        note.setContent(noteDto.getContent());
        note.setDoctorstaffId(noteDto.getDoctorstaffId());
        note.setPatientId(patient.getUserId());
        return note;
    }
}
