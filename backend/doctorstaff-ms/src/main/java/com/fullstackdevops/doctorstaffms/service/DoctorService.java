package com.fullstackdevops.doctorstaffms.service;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.model.Doctor;

public interface DoctorService {

    DoctorDto createDoctor(DoctorDto doctorDto);
}
