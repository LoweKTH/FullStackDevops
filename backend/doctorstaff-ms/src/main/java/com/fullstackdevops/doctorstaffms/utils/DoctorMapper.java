package com.fullstackdevops.doctorstaffms.utils;

import com.fullstackdevops.doctorstaffms.model.Doctor;
import com.fullstackdevops.doctorstaffms.dto.DoctorDto;

public class DoctorMapper {

    public static DoctorDto toDto(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setSocialSecurityNumber(doctor.getSocialSecurityNumber());
        doctorDto.setUserId(doctor.getUserId());
        doctorDto.setFirstname(doctor.getFirstname());
        doctorDto.setLastname(doctor.getLastname());
        doctorDto.setEmail(doctor.getEmail());
        doctorDto.setPhoneNumber(doctor.getPhoneNumber());
        doctorDto.setSpecialty(doctor.getSpecialty());

        return doctorDto;
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
