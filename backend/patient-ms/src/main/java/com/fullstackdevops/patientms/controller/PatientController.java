package com.fullstackdevops.patientms.controller;

import com.fullstackdevops.patientms.dto.DiagnosisDto;
import com.fullstackdevops.patientms.dto.DoctorDto;
import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/addPatient")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto){
        return new ResponseEntity<>(patientService.createPatient(patientDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
            PatientDto patientDto = patientService.getPatientById(id);
            return ResponseEntity.ok(patientDto);
    }


    @PostMapping("/{patientId}/notes")
    public ResponseEntity<NoteDto> addNoteToPatient(@PathVariable Long patientId, @RequestBody NoteDto noteDto) {
        Long doctorId = noteDto.getDoctorId();
        NoteDto createdNote = patientService.addNoteToPatient(patientId, noteDto, doctorId);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping("/{patientId}/diagnosis")
    public DiagnosisDto addDiagnosis(@PathVariable Long patientId, @RequestBody DiagnosisDto diagnosisDto) {
        Long doctorId = diagnosisDto.getDoctorId();
        return patientService.addDiagnosisToPatient(patientId, diagnosisDto, doctorId);
    }

    @GetMapping("/{patientId}/doctors")
    public ResponseEntity<List<DoctorDto>> getDoctorsForPatient(@PathVariable Long patientId){
        List<DoctorDto> doctors = patientService.getDoctorsForPatient(patientId);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}/getpatients")
    public ResponseEntity<List<Long>> getPatientsByDoctorId(@PathVariable Long doctorId) {
        List<Long> patientIds = patientService.getPatientsByDoctorId(doctorId);
        return ResponseEntity.ok(patientIds);
    }

}
