package com.fullstackdevops.patientms.service;

import com.fullstackdevops.patientms.dto.*;
import jakarta.transaction.Transactional;

import java.util.List;


public interface PatientService {

    List<PatientDto> getAllPatients();
    PatientDto getPatientById(String id);
    PatientDto createPatient(PatientDto patientDto);

    NoteDto addNoteToPatient(String patientId, NoteDto noteDto, String doctorstaffId);

    List<NoteDto> getNotesForPatient(String patientId);


    List<DiagnosisDto> getDiagnosesForPatient(String patientId);

    @Transactional

    DiagnosisDto addDiagnosisToPatient(String patientId, DiagnosisDto diagnosisDto, String doctorstaffId);




    DoctorStaffDto getDoctorstaffForPatient(String patientId);

    List<String> getPatientsByDoctorstaffId(String doctorId);

    List<DoctorStaffDto> getAllDoctorsForPatient(Long patientId);
    List<PatientDto> getPatientsByDiagnosisName(String diagnosisName);

    List<PatientDto> getPatientsByName(String name);
}
