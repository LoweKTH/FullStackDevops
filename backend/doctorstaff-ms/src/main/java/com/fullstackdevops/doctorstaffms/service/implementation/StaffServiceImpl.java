package com.fullstackdevops.doctorstaffms.service.implementation;

import com.fullstackdevops.doctorstaffms.dto.PatientDto;
import com.fullstackdevops.doctorstaffms.dto.StaffDto;
import com.fullstackdevops.doctorstaffms.model.Staff;
import com.fullstackdevops.doctorstaffms.repository.StaffRepository;
import com.fullstackdevops.doctorstaffms.service.StaffService;
import com.fullstackdevops.doctorstaffms.utils.StaffMapper;
import com.fullstackdevops.doctorstaffms.utils.StaffNotFoundException;
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
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final RestTemplate restTemplate;

    @Override
    public StaffDto createStaff(StaffDto staffDto) {
        Staff staff = StaffMapper.toEntity(staffDto);
        staffRepository.save(staff);
        return StaffMapper.toDto(staff);
    }

    @Override
    public StaffDto getStaffById(String staffId) {
        Staff staff = staffRepository.findByUserId(staffId).orElse(null);

        return StaffMapper.toDto(staff);
    }

    @Override
    public List<PatientDto> getPatientsForStaff(String staffId) {

        String token = getJwtTokenFromSecurityContext();
        System.out.println("TOKEN:    "+token);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);


        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "http://patient-ms:8080/api/patients/" + staffId + "/doctorstaffgetpatients";
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<String>>() {}
        );
        List<String> patientIds = response.getBody();

        List<PatientDto> patientDtos = new ArrayList<>();

        for (String patientId : patientIds) {
            String patientUrl = "http://patient-ms:8080/api/patients/" + patientId;

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
