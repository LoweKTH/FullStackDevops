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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public PatientDto getPatientById(String id){
        Patient patient = patientRepository.findByUserId(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient with specified ID not found"));
        System.out.println(patient);
        return PatientMapper.toDto(patient);
    }



    @Transactional
    @Override
    public NoteDto addNoteToPatient(String patientId, NoteDto noteDto, String doctorstaffId) {
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Note note = NoteMapper.toEntity(noteDto, patient);

        note.setDoctorstaffId(doctorstaffId);
        note = noteRepository.save(note);

        return NoteMapper.toDto(note);
    }

    @Override
    public List<NoteDto> getNotesForPatient(String patientId) {
        List<Note> notes = noteRepository.findByPatientId(patientId);

        // Initialize the list of NoteDto objects
        List<NoteDto> noteDtos = new ArrayList<>();

        // Get the JWT token from the security context
        String token = getJwtTokenFromSecurityContext(); // Assumes this method retrieves the token
        System.out.println("TOKEN:    "+token);
        // Set the authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println(notes);
        for (Note note : notes) {
            try {
                // First, try to get the doctor details
                ResponseEntity<DoctorDto> doctorResponse = restTemplate.exchange(
                        "https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/doctors/" + note.getDoctorstaffId(),
                        HttpMethod.GET,
                        entity,
                        DoctorDto.class
                );

                if (doctorResponse.getStatusCode().is2xxSuccessful() && doctorResponse.getBody() != null) {
                    DoctorDto doctor = doctorResponse.getBody();
                    System.out.println(doctor);
                    NoteDto noteDto = NoteMapper.toDto(note);
                    noteDto.setDoctorstaffName(doctor.getFirstname() + " " + doctor.getLastname());
                    noteDto.setRole("Doctor");
                    noteDtos.add(noteDto);
                }else{
                    try {
                        // If no doctor is found, fetch the staff details
                        ResponseEntity<StaffDto> staffResponse = restTemplate.exchange(
                                "https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/staff/" + note.getDoctorstaffId(),
                                HttpMethod.GET,
                                entity,
                                StaffDto.class
                        );

                        if (staffResponse.getStatusCode().is2xxSuccessful() && staffResponse.getBody() != null) {
                            StaffDto staff = staffResponse.getBody();
                            NoteDto noteDto = NoteMapper.toDto(note);
                            noteDto.setDoctorstaffName(staff.getFirstname() + " " + staff.getLastname());
                            noteDto.setRole("Staff");
                            noteDtos.add(noteDto);
                        }
                    } catch (Exception ex) {
                        // Log or handle the exception if needed
                    }

                }
            } catch (Exception ex) {
                // Log or handle the exception if needed
            }


        }

        return noteDtos;
    }


    @Override
    public List<DiagnosisDto> getDiagnosesForPatient(String patientId) {
        List<Diagnosis> diagnoses = diagnosisRepository.findByPatientId(patientId);
        List<DiagnosisDto> diagnosisDtos = new ArrayList<>();

        String token = getJwtTokenFromSecurityContext(); // Method from above

        // Set the authorization header for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        for (Diagnosis diagnosis : diagnoses) {
            try {
                // First, try to get the doctor details
                ResponseEntity<DoctorDto> doctorResponse = restTemplate.exchange(
                        "https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/doctors/" + diagnosis.getDoctorstaffId(),
                        HttpMethod.GET,
                        entity,
                        DoctorDto.class
                );

                if (doctorResponse.getStatusCode().is2xxSuccessful() && doctorResponse.getBody() != null) {
                    DoctorDto doctor = doctorResponse.getBody();
                    DiagnosisDto diagnosisDto = DiagnosisMapper.toDto(diagnosis);
                    diagnosisDto.setDoctorstaffName(doctor.getFirstname() + " " + doctor.getLastname());
                    diagnosisDto.setRole("Doctor");
                    diagnosisDtos.add(diagnosisDto);
                    continue; // Skip to the next iteration if doctor is found
                }
            } catch (Exception ex) {
                // Log or handle the exception if needed
            }

            try {
                // If no doctor is found, fetch the staff details
                ResponseEntity<StaffDto> staffResponse = restTemplate.exchange(
                        "https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/staff/" + diagnosis.getDoctorstaffId(),
                        HttpMethod.GET,
                        entity,
                        StaffDto.class
                );

                if (staffResponse.getStatusCode().is2xxSuccessful() && staffResponse.getBody() != null) {
                    StaffDto staff = staffResponse.getBody();
                    DiagnosisDto diagnosisDto = DiagnosisMapper.toDto(diagnosis);
                    diagnosisDto.setDoctorstaffName(staff.getFirstname() + " " + staff.getLastname());
                    diagnosisDto.setRole("Staff");
                    diagnosisDtos.add(diagnosisDto);
                }
            } catch (Exception ex) {
                // Log or handle the exception if needed
            }
        }

        return diagnosisDtos;
    }



    @Override
    @Transactional
    public DiagnosisDto addDiagnosisToPatient(String patientId, DiagnosisDto diagnosisDto, String doctorId) {
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with specified ID not found"));

        Diagnosis diagnosis = DiagnosisMapper.toEntity(diagnosisDto,patient);
        diagnosis = diagnosisRepository.save(diagnosis);
        return DiagnosisMapper.toDto(diagnosis);
    }

    @Override
    public DoctorStaffDto getDoctorstaffForPatient(String patientId) {

        String token = getJwtTokenFromSecurityContext(); // Assumes this method retrieves the token
        System.out.println("TOKEN:    " + token);

        // Set the authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);


        Set<String> doctorstaffIdsSet = new HashSet<>();
        List<String> noteDoctorStaffIds = noteRepository.findDistinctDoctorstaffByPatientId(patientId);
        List<String> diagnosisDoctorStaffIds = diagnosisRepository.findDistinctDoctorstaffByPatientId(patientId);
        doctorstaffIdsSet.addAll(noteDoctorStaffIds);
        doctorstaffIdsSet.addAll(diagnosisDoctorStaffIds);

        List<String> doctorstaffIds = new ArrayList<>(doctorstaffIdsSet);

        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<StaffDto> staffDtos = new ArrayList<>();

        for (String doctorstaffId : doctorstaffIds) {
            // Fetch doctor information
            String doctorUrl = "https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/doctors/" + doctorstaffId;
            try {
                ResponseEntity<DoctorDto> doctorResponse = restTemplate.exchange(
                        doctorUrl,
                        HttpMethod.GET,
                        entity, // Pass the headers with token
                        DoctorDto.class
                );
                if (doctorResponse.getBody() != null) {
                    doctorDtos.add(doctorResponse.getBody());
                }
            } catch (Exception ex) {
                System.err.println("Failed to fetch doctor info for ID: " + doctorstaffId + ", Error: " + ex.getMessage());
            }

            // Fetch staff information
            String staffUrl = "https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/staff/" + doctorstaffId;
            try {
                ResponseEntity<StaffDto> staffResponse = restTemplate.exchange(
                        staffUrl,
                        HttpMethod.GET,
                        entity, // Pass the headers with token
                        StaffDto.class
                );
                if (staffResponse.getBody() != null) {
                    staffDtos.add(staffResponse.getBody());
                }
            } catch (Exception ex) {
                System.err.println("Failed to fetch staff info for ID: " + doctorstaffId + ", Error: " + ex.getMessage());
            }
        }

        return new DoctorStaffDto(doctorDtos, staffDtos);
    }



    @Override
    public List<String> getPatientsByDoctorstaffId(String doctorstaffId) {

        Set<String> patientIdsSet = new HashSet<>();

        List<String>  notePatientIds = noteRepository.findDistinctPatientsByDoctorstaffId(doctorstaffId);
        List<String> diagnosisPatientIds = diagnosisRepository.findDistinctPatientsByDoctorstaffId(doctorstaffId);
        patientIdsSet.addAll(notePatientIds);
        patientIdsSet.addAll(diagnosisPatientIds);

        List<String> patientIdsList = new ArrayList<>(patientIdsSet);

        return patientIdsList;
    }

    private String getJwtTokenFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getTokenValue();
        }
        return null;
    }

}
