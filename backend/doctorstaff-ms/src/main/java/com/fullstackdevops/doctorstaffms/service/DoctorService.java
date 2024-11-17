package com.fullstackdevops.doctorstaffms.service;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.model.Doctor;

import java.util.List;

public interface DoctorService {

    DoctorDto createDoctor(DoctorDto doctorDto);
    DoctorDto getDoctorById(Long doctorId);
    List<PatientDto> getPatientsForDoctor(Long doctorId);
}
