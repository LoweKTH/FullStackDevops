package com.fullstackdevops.doctorstaffms.utils;

import com.fullstackdevops.doctorstaffms.model.Doctor;
import com.fullstackdevops.doctorstaffms.dto.DoctorDto;

public class DoctorMapper {

    public static DoctorDto toDto(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDto dto = new DoctorDto();
        dto.setSocialSecurityNumber(doctor.getSocialSecurityNumber());
        dto.setUserId(doctor.getUserId());
        dto.setFirstname(doctor.getFirstname());
        dto.setLastname(doctor.getLastname());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        dto.setSpecialty(doctor.getSpecialty());

        return dto;
    }

    public static Doctor toEntity(DoctorDto doctorDto) {
        if (doctorDto == null) {
            return null;
        }

        Doctor doctor = new Doctor();
        doctor.setSocialSecurityNumber(doctorDto.getSocialSecurityNumber());
        doctor.setUserId(doctorDto.getUserId());
        doctor.setFirstname(doctorDto.getFirstname());
        doctor.setLastname(doctorDto.getLastname());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setSpecialty(doctorDto.getSpecialty());

        return doctor;
    }
}
