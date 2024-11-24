package com.fullstackdevops.patientms.controller;

import com.fullstackdevops.patientms.dto.*;
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
        Long doctorstaffId= noteDto.getDoctorstaffId();
        NoteDto createdNote = patientService.addNoteToPatient(patientId, noteDto ,doctorstaffId);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @GetMapping("/{patientId}/getnotes")
    public ResponseEntity<List<NoteDto>> getNotesForPatient(@PathVariable Long patientId) {
        List<NoteDto> notes = patientService.getNotesForPatient(patientId);
        return new ResponseEntity<>(notes, HttpStatus.CREATED);
    }

    @GetMapping("/{patientId}/getdiagnoses")
    public ResponseEntity<List<DiagnosisDto>> getDiagnosesForPatient(@PathVariable Long patientId) {
        List<DiagnosisDto> diagnoses = patientService.getDiagnosesForPatient(patientId);
        return new ResponseEntity<>(diagnoses, HttpStatus.CREATED);
    }

    /*@PostMapping("/{patientId}/staffnotes")
    public ResponseEntity<NoteDto> addNoteToPatientAsStaff(@PathVariable Long patientId, @RequestBody NoteDto noteDto) {
        Long staffId = noteDto.getStaffId();
        NoteDto createdNote = patientService.addNoteToPatientAsStaff(patientId, noteDto ,staffId);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }*/

    @GetMapping("/")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping("/{patientId}/diagnosis")
    public DiagnosisDto addDiagnosis(@PathVariable Long patientId, @RequestBody DiagnosisDto diagnosisDto) {
        Long doctorstaffId = diagnosisDto.getDoctorstaffId();
        return patientService.addDiagnosisToPatient(patientId, diagnosisDto, doctorstaffId);
    }

    @GetMapping("/{patientId}/doctorstaff")
    public ResponseEntity<DoctorStaffDto> getDoctorsForPatient(@PathVariable Long patientId){
        DoctorStaffDto doctors = patientService.getDoctorstaffForPatient(patientId);
        return ResponseEntity.ok(doctors);
    }

    /*@GetMapping("/{patientId}/staffs")
    public ResponseEntity<List<StaffDto>> getStaffsForPatient(@PathVariable Long patientId){
        List<StaffDto> staffs = patientService.getStaffsForPatient(patientId);
        return ResponseEntity.ok(staffs);
    }*/

    @GetMapping("/{doctorstaffId}/doctorstaffgetpatients")
    public ResponseEntity<List<Long>> getPatientsByDoctorstaffId(@PathVariable Long doctorstaffId) {
        List<Long> patientIds = patientService.getPatientsByDoctorstaffId(doctorstaffId);
        return ResponseEntity.ok(patientIds);
    }



}
