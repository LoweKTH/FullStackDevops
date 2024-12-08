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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    public StaffDto getStaffById(Long staffId) {
        Staff staff = staffRepository.findByUserId(staffId).orElse(null);

        return StaffMapper.toDto(staff);
    }

    @Override
    public List<PatientDto> getPatientsForStaff(Long staffId) {

        String url = "http://patient-ms:80/api/patients/" + staffId + "/doctorstaffgetpatients";
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
