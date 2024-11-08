package com.fullstackdevops.patientms.utils;

import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.model.Note;

public class NoteMapper {

    public static NoteDto toDto(Note note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setDoctorId(note.getDoctorId());
        return dto;
    }

    public static Note toEntity(NoteDto dto) {
        Note note = new Note();
        note.setContent(dto.getContent());
        note.setDoctorId(dto.getDoctorId());
        return note;
    }
}
