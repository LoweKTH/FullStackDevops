package com.fullstackdevops.doctorstaffms.controller;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto){
        return new ResponseEntity<>(doctorService.createDoctor(doctorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long doctorId){
        DoctorDto doctor = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<PatientDto>> getPatientsForDoctor(@PathVariable Long doctorId){
        List<PatientDto> patients = doctorService.getPatientsForDoctor(doctorId);
        return ResponseEntity.ok(patients);
    }
}
