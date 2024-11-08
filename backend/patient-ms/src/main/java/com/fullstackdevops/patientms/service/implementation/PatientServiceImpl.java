package com.fullstackdevops.patientms.service.implementation;

import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.model.Note;
import com.fullstackdevops.patientms.repository.NoteRepository;
import com.fullstackdevops.patientms.repository.PatientRepository;
import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.model.Patient;
import com.fullstackdevops.patientms.service.PatientService;
import com.fullstackdevops.patientms.utils.NoteMapper;
import com.fullstackdevops.patientms.utils.PatientMapper;

import com.fullstackdevops.patientms.utils.PatientNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;
    private final NoteRepository noteRepository;

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

    @Override
    @Transactional
    public NoteDto addNoteToPatient(Long patientId, NoteDto noteDto, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Note note = new Note();
        note.setContent(noteDto.getContent());
        note.setDoctorId(doctorId);
        note.setPatient(patient);

        note = noteRepository.save(note);

        return NoteMapper.toDto(note);
    }

}
