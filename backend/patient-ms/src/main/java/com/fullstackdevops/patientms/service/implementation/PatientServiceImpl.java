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
import java.util.List;

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
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient with specified ID not found"));

        return PatientMapper.toDto(patient);
    }



    @Transactional
    @Override
    public NoteDto addNoteToPatientAsDoctor(Long patientId, NoteDto noteDto, Long doctorId) {
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Note note = NoteMapper.toEntity(noteDto, patient);

        note.setDoctorId(doctorId);
        note = noteRepository.save(note);

        return NoteMapper.toDto(note);
    }

    @Transactional
    @Override
    public NoteDto addNoteToPatientAsStaff(Long patientId, NoteDto noteDto, Long staffId) {
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Note note = NoteMapper.toEntity(noteDto, patient);

        note.setStaffId(staffId);
        note = noteRepository.save(note);

        return NoteMapper.toDto(note);
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
    public List<DoctorDto> getDoctorsForPatient(Long patientId){
        List<Long>  doctorIds = noteRepository.findDistinctDoctorsByPatientId(patientId);
        List<DoctorDto> doctorDtos = new ArrayList<>();

        for (Long doctorId : doctorIds){
            DoctorDto doctor = restTemplate.getForObject("http://doctorstaff-ms:8080/api/doctors/" +doctorId,DoctorDto.class);
            doctorDtos.add(doctor);
        }
        return doctorDtos;

    }

    @Override
    public List<StaffDto> getStaffsForPatient(Long patientId){
        List<Long>  staffIds = noteRepository.findDistinctStaffsByPatientId(patientId);
        List<StaffDto> staffDtos = new ArrayList<>();

        for (Long staffId : staffIds){
            StaffDto staff = restTemplate.getForObject("http://doctorstaff-ms:8080/api/staff/" +staffId,StaffDto.class);
            staffDtos.add(staff);
        }
        return staffDtos;
    }
    @Override
    public List<Long> getPatientsByDoctorId(Long doctorId) {
        List<Long>  patientIds = noteRepository.findDistinctPatientsByDoctorId(doctorId);
        return patientIds;
    }
    @Override
    public List<Long> getPatientsByStaffId(Long staffId) {
        List<Long>  patientIds = noteRepository.findDistinctPatientsByStaffId(staffId);
        return patientIds;
    }


}
