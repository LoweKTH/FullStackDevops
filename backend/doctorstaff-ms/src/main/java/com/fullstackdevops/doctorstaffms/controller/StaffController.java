package com.fullstackdevops.doctorstaffms.controller;

import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StaffDto> getStaffById(@PathVariable Long staffId){
        StaffDto staff = staffService.getStaffById(staffId);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{staffId}/patients")
    public ResponseEntity<List<PatientDto>> getPatientsForStaff(@PathVariable Long staffId){
        List<PatientDto> patients = staffService.getPatientsForStaff(staffId);
        return ResponseEntity.ok(patients);
    }
}