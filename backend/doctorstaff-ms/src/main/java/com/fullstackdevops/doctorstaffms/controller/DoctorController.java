package com.fullstackdevops.doctorstaffms.controller;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "https://fullstack24-frontendnew.app.cloud.cbh.kth.se", allowCredentials = "true")
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto){
        return new ResponseEntity<>(doctorService.createDoctor(doctorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{doctorId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_STAFF')")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable String doctorId){
        DoctorDto doctor = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/{doctorId}/patients")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_STAFF')")
    public ResponseEntity<List<PatientDto>> getPatientsForDoctor(@PathVariable String doctorId){
        List<PatientDto> patients = doctorService.getPatientsForDoctor(doctorId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/searchDoctors")
    public ResponseEntity<List<DoctorDto>> searchDoctors(@RequestParam(name = "name", required = false) String name) {
        System.out.println("search: " + name);
        List<DoctorDto> doctors = doctorService.searchDoctors(name);
        System.out.println("hit kommer vi");
        return ResponseEntity.ok(doctors);
    }

}
