package com.fullstackdevops.doctorstaffms.controller;

import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}