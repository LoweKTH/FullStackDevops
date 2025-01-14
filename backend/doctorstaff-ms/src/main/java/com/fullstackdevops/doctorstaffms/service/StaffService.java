package com.fullstackdevops.doctorstaffms.service;


import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.dto.StaffDto;

import java.util.List;

public interface StaffService {

    StaffDto createStaff(StaffDto staffDto);

    StaffDto getStaffById(String staffId);

    List<PatientDto> getPatientsForStaff(String staffId);
}
