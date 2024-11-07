package com.fullstackdevops.patientms.service.implementation;

import com.fullstackdevops.patientms.repository.PatientRepository;
import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.model.Patient;
import com.fullstackdevops.patientms.service.PatientService;
import com.fullstackdevops.patientms.utils.PatientMapper;

import com.fullstackdevops.patientms.utils.PatientNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;

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

}
