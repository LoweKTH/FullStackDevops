package com.fullstackdevops.patientms.controller;

import com.fullstackdevops.patientms.dto.NoteDto;
import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/addPatient")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto){
        return new ResponseEntity<>(patientService.createPatient(patientDto), HttpStatus.CREATED);
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
}
