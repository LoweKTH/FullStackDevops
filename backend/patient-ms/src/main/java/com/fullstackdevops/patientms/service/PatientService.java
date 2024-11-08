package com.fullstackdevops.patientms.service;

import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.model.Patient;

import java.util.List;


public interface PatientService {

    List<PatientDto> getAllPatients();
    PatientDto getPatientById(Long id);
    PatientDto createPatient(PatientDto patientDto);
    NoteDto addNoteToPatient(Long patientId, NoteDto noteDto,Long doctorId);
}
