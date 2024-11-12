package com.fullstackdevops.doctorstaffms.service.implementation;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.model.Doctor;
import com.fullstackdevops.doctorstaffms.repository.DoctorRepository;
import com.fullstackdevops.doctorstaffms.service.DoctorService;
import com.fullstackdevops.doctorstaffms.utils.DoctorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = DoctorMapper.toEntity(doctorDto);
        doctorRepository.save(doctor);
        return DoctorMapper.toDto(doctor);
    }


}
