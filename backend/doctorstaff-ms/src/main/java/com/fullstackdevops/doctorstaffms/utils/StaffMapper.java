package com.fullstackdevops.doctorstaffms.utils;

import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.model.Doctor;
import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.model.Staff;

public class StaffMapper {

    public static StaffDto toDto(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffDto dto = new StaffDto();
        dto.setSocialSecurityNumber(staff.getSocialSecurityNumber());
        dto.setUserId(staff.getUserId());
        dto.setFirstname(staff.getFirstname());
        dto.setLastname(staff.getLastname());
        dto.setEmail(staff.getEmail());
        dto.setPhoneNumber(staff.getPhoneNumber());
        dto.setDescription(staff.getDescription());

        return dto;
    }

    public static Staff toEntity(StaffDto staffDto) {
        if (staffDto == null) {
            return null;
        }

        Staff staff = new Staff();
        staff.setSocialSecurityNumber(staffDto.getSocialSecurityNumber());
        staff.setUserId(staffDto.getUserId());
        staff.setFirstname(staffDto.getFirstname());
        staff.setLastname(staffDto.getLastname());
        staff.setEmail(staffDto.getEmail());
        staff.setPhoneNumber(staffDto.getPhoneNumber());
        staff.setDescription(staffDto.getDescription());

        return staff;
    }
}
