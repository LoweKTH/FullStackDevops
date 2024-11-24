package com.fullstackdevops.patientms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorStaffDto {
    private List<DoctorDto> doctors;
    private List<StaffDto> staffs;
}
