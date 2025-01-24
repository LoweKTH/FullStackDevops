package com.fullstackdevops.doctorstaffms.controller;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/addStaff")
    public ResponseEntity<StaffDto> createStaff(@RequestBody StaffDto staffDto){
        return new ResponseEntity<>(staffService.createStaff(staffDto), HttpStatus.CREATED);
    }

    @GetMapping("/{staffId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_STAFF')")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable String staffId){
        StaffDto staff = staffService.getStaffById(staffId);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{staffId}/patients")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<List<PatientDto>> getPatientsForStaff(@PathVariable String staffId){
        List<PatientDto> patients = staffService.getPatientsForStaff(staffId);
        return ResponseEntity.ok(patients);
    }
}