package com.fullstackdevops.patientms.service;

import com.fullstackdevops.patientms.dto.DiagnosisDto;
import com.fullstackdevops.patientms.dto.DoctorStaffDto;
import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.dto.PatientDto;
import jakarta.transaction.Transactional;

import java.util.List;


public interface PatientService {

    List<PatientDto> getAllPatients();
    PatientDto getPatientById(Long id);
    PatientDto createPatient(PatientDto patientDto);

    NoteDto addNoteToPatient(Long patientId, NoteDto noteDto, Long doctorstaffId);

    List<NoteDto> getNotesForPatient(Long patientId);


    List<DiagnosisDto> getDiagnosesForPatient(Long patientId);

    @Transactional

    DiagnosisDto addDiagnosisToPatient(Long patientId, DiagnosisDto diagnosisDto, Long doctorstaffId);




    DoctorStaffDto getDoctorstaffForPatient(Long patientId);

    List<Long> getPatientsByDoctorstaffId(Long doctorId);

    List<DoctorStaffDto> getAllDoctorsForPatient(Long patientId);
    List<PatientDto> getPatientsByDiagnosisName(String diagnosisName);

}
