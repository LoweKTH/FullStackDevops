package com.fullstackdevops.patientms.service;

import com.fullstackdevops.patientms.dto.*;

import java.util.List;


public interface PatientService {

    List<PatientDto> getAllPatients();
    PatientDto getPatientById(Long id);
    PatientDto createPatient(PatientDto patientDto);
    NoteDto addNoteToPatient(Long patientId, NoteDto noteDto);
    DiagnosisDto addDiagnosisToPatient(Long patientId, DiagnosisDto diagnosisDto, Long doctorId);
    List<DoctorDto> getDoctorsForPatient(Long patientId);

    List<StaffDto> getStaffsForPatient(Long patientId);

    List<Long> getPatientsByDoctorId(Long doctorId);

    List<Long> getPatientsByStaffId(Long staffId);
}
