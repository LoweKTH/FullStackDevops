package com.fullstackdevops.doctorstaffms.service.implementation;

import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.model.Doctor;
import com.fullstackdevops.doctorstaffms.model.Staff;
import com.fullstackdevops.doctorstaffms.repository.DoctorRepository;
import com.fullstackdevops.doctorstaffms.service.DoctorService;
import com.fullstackdevops.doctorstaffms.utils.DoctorMapper;
import com.fullstackdevops.doctorstaffms.utils.DoctorNotFoundException;
import com.fullstackdevops.doctorstaffms.utils.StaffMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final RestTemplate restTemplate;


    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = DoctorMapper.toEntity(doctorDto);
        doctorRepository.save(doctor);
        return DoctorMapper.toDto(doctor);
    }

    @Override
    public DoctorDto getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findByUserId(doctorId).orElse(null);

        return DoctorMapper.toDto(doctor);
    }

    @Override
    public List<PatientDto> getPatientsForDoctor(Long doctorId) {

        String url = "http://patient-ms:80/api/patients/" + doctorId + "/doctorstaffgetpatients";
        ResponseEntity<List<Long>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Long>>() {}
        );
        List<Long> patientIds = response.getBody();

        List<PatientDto> patientDtos = new ArrayList<>();
        for (Long patientId : patientIds) {
            PatientDto patient = restTemplate.getForObject("http://patient-ms:80/api/patients/" + patientId, PatientDto.class);
            patientDtos.add(patient);
        }
        return patientDtos;
    }
}
