package com.fullstackdevops.searchms.service;

import com.fullstackdevops.searchms.config.PatientMsClient;
import com.fullstackdevops.searchms.dto.DoctorDto;
import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.dto.StaffDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchService {

    private final PatientMsClient patientMsClient;

    public SearchService(@RestClient PatientMsClient patientMsClient) {this.patientMsClient = patientMsClient;}



    public DoctorStaffDto searchDoctorsForPatient(Long patientId, String doctorName) {
        DoctorStaffDto doctorStaffDto = patientMsClient.getDoctorsForPatient(patientId);

        if (doctorStaffDto == null || doctorStaffDto.getDoctors() == null || doctorStaffDto.getStaffs() == null) {
            return new DoctorStaffDto(List.of(), List.of());
        }

        List<DoctorDto> filteredDoctors = doctorStaffDto.getDoctors().stream()
                .filter(doctor -> doctorName == null || doctor.getFirstname().toLowerCase().contains(doctorName.toLowerCase()))
                .collect(Collectors.toList());

        return new DoctorStaffDto(filteredDoctors, doctorStaffDto.getStaffs());
    }

    public Uni<List<PatientDto>> searchPatientsByDiagnosis(String diagnosis) {
        return patientMsClient.getPatientsByDiagnosis(diagnosis);
    }
}
