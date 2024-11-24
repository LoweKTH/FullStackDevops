package com.fullstackdevops.patientms.service.implementation;

import com.fullstackdevops.patientms.dto.*;
import com.fullstackdevops.patientms.model.Diagnosis;
import com.fullstackdevops.patientms.model.Note;
import com.fullstackdevops.patientms.repository.DiagnosisRepository;
import com.fullstackdevops.patientms.repository.NoteRepository;
import com.fullstackdevops.patientms.repository.PatientRepository;
import com.fullstackdevops.patientms.model.Patient;
import com.fullstackdevops.patientms.service.PatientService;
import com.fullstackdevops.patientms.utils.DiagnosisMapper;
import com.fullstackdevops.patientms.utils.NoteMapper;
import com.fullstackdevops.patientms.utils.PatientMapper;

import com.fullstackdevops.patientms.utils.PatientNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;
    private final NoteRepository noteRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final RestTemplate restTemplate;


    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = PatientMapper.toEntity(patientDto);
        patientRepository.save(patient);
        return PatientMapper.toDto(patient);
    }

    @Override
    public List<PatientDto> getAllPatients(){
        List<Patient> patient = patientRepository.findAll();
        List <PatientDto> patientDtos = new ArrayList<>();

        for (Patient p : patient) {
            patientDtos.add(PatientMapper.toDto(p));
        }

        return patientDtos;
    }

    @Override
    public PatientDto getPatientById(Long id){
        Patient patient = patientRepository.findByUserId(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient with specified ID not found"));

        return PatientMapper.toDto(patient);
    }



    @Transactional
    @Override
    public NoteDto addNoteToPatient(Long patientId, NoteDto noteDto, Long doctorstaffId) {
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Note note = NoteMapper.toEntity(noteDto, patient);

        note.setDoctorstaffId(doctorstaffId);
        note = noteRepository.save(note);

        return NoteMapper.toDto(note);
    }

    @Override
    public List<NoteDto> getNotesForPatient(Long patientId) {
        List<Note> notes = noteRepository.findByPatientId(patientId);

        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : notes) {

                UserDto user = restTemplate.getForObject("http://user-ms:8080/api/user/"+note.getDoctorstaffId() , UserDto.class);
            NoteDto noteDto = NoteMapper.toDto(note);

            noteDto.setDoctorstaffName(user.getUsername());
            noteDto.setRole(user.getRole());

            noteDtos.add(noteDto);
        }
        return noteDtos;
    }

    @Override
    public List<DiagnosisDto> getDiagnosesForPatient(Long patientId) {
        List<Diagnosis> diagnoses = diagnosisRepository.findByPatientId(patientId);
        List<DiagnosisDto> diagnosisDtos = new ArrayList<>();

        for (Diagnosis diagnosis : diagnoses) {
            UserDto user = restTemplate.getForObject("http://user-ms:8080/api/user/"+diagnosis.getDoctorstaffId() , UserDto.class);
            DiagnosisDto diagnosisDto = DiagnosisMapper.toDto(diagnosis);

            diagnosisDto.setDoctorstaffName(user.getUsername());
            diagnosisDto.setRole(user.getRole());

            diagnosisDtos.add(diagnosisDto);
        }

        return diagnosisDtos;
    }



    @Override
    @Transactional
    public DiagnosisDto addDiagnosisToPatient(Long patientId, DiagnosisDto diagnosisDto, Long doctorId) {
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Diagnosis diagnosis = DiagnosisMapper.toEntity(diagnosisDto,patient);
        diagnosis = diagnosisRepository.save(diagnosis);
        return DiagnosisMapper.toDto(diagnosis);
    }

    @Override
    public DoctorStaffDto getDoctorstaffForPatient(Long patientId) {
        Set<Long> doctorstaffIdsSet = new HashSet<>();
        List<Long> noteDoctorStaffIds = noteRepository.findDistinctDoctorstaffByPatientId(patientId);
        List<Long> diagnosisDoctorStaffIds = diagnosisRepository.findDistinctDoctorstaffByPatientId(patientId);
        doctorstaffIdsSet.addAll(noteDoctorStaffIds);
        doctorstaffIdsSet.addAll(diagnosisDoctorStaffIds);

        List<Long> doctorstaffIds = new ArrayList<>(doctorstaffIdsSet);

        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<StaffDto> staffDtos = new ArrayList<>();

        for (Long doctorstaffId : doctorstaffIds) {
            DoctorDto doctor = restTemplate.getForObject("http://doctorstaff-ms:8080/api/doctors/" + doctorstaffId, DoctorDto.class);
            if (doctor != null) {
                doctorDtos.add(doctor);
            }

            StaffDto staff = restTemplate.getForObject("http://doctorstaff-ms:8080/api/staff/" + doctorstaffId, StaffDto.class);
            if (staff != null) {
                staffDtos.add(staff);
            }
        }

        return new DoctorStaffDto(doctorDtos, staffDtos);
    }



    @Override
    public List<Long> getPatientsByDoctorstaffId(Long doctorstaffId) {

        Set<Long> patientIdsSet = new HashSet<>();

        List<Long>  notePatientIds = noteRepository.findDistinctPatientsByDoctorstaffId(doctorstaffId);
        List<Long> diagnosisPatientIds = diagnosisRepository.findDistinctPatientsByDoctorstaffId(doctorstaffId);
        patientIdsSet.addAll(notePatientIds);
        patientIdsSet.addAll(diagnosisPatientIds);

        List<Long> patientIdsList = new ArrayList<>(patientIdsSet);

        return patientIdsList;
    }


}
