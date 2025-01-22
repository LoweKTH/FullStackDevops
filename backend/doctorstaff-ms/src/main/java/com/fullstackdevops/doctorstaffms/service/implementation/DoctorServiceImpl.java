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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public DoctorDto getDoctorById(String doctorId) {
        Doctor doctor = doctorRepository.findByUserId(doctorId).orElse(null);

        return DoctorMapper.toDto(doctor);
    }

    @Override
    public List<PatientDto> getPatientsForDoctor(String doctorId) {

        String token = getJwtTokenFromSecurityContext();
        System.out.println("TOKEN:    "+token);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);


        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://fullstack24-patient.app.cloud.cbh.kth.se/api/patients/" + doctorId + "/doctorstaffgetpatients";
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<String>>() {}
        );
        List<String> patientIds = response.getBody();

        List<PatientDto> patientDtos = new ArrayList<>();
        for (String patientId : patientIds) {
            String patientUrl = "https://fullstack24-patient.app.cloud.cbh.kth.se/api/patients/" + patientId;

            ResponseEntity<PatientDto> patientResponse = restTemplate.exchange(
                    patientUrl,
                    HttpMethod.GET,
                    entity,
                    PatientDto.class
            );

            patientDtos.add(patientResponse.getBody());
        }
        return patientDtos;
    }

    private String getJwtTokenFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getTokenValue();
        }
        return null;
    }
}
