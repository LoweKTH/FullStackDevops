package com.fullstackdevops.searchms.service;

import com.fullstackdevops.searchms.config.DoctorMSClient;
import com.fullstackdevops.searchms.config.PatientMSClient;
import com.fullstackdevops.searchms.dto.*;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchService {

    @Inject
    @RestClient
    DoctorMSClient doctorMSClient;

    @Inject
    @RestClient
    PatientMSClient patientMSClient;

    public DoctorStaffDto searchDoctorsForPatient(String patientId, String doctorName) {
        DoctorStaffDto doctorStaffDto = patientMSClient.getDoctorsForPatient(patientId);

        if (doctorStaffDto == null || doctorStaffDto.getDoctors() == null || doctorStaffDto.getStaffs() == null) {
            return new DoctorStaffDto(List.of(), List.of());
        }

        List<DoctorDto> filteredDoctors = doctorStaffDto.getDoctors().stream()
                .filter(doctor -> doctorName == null || doctor.getFirstname().toLowerCase().contains(doctorName.toLowerCase()))
                .collect(Collectors.toList());

        return new DoctorStaffDto(filteredDoctors, doctorStaffDto.getStaffs());
    }

    public Uni<List<PatientDto>> searchPatientsByDiagnosis(String diagnosis) {
        return patientMSClient.getPatientsByDiagnosis(diagnosis);
    }

    public Uni<List<DoctorWithPatients>> getDoctorsWithPatients(String name) {
        return searchDoctors(name)
                .onItem().transformToUni(doctors ->
                        Uni.combine().all()
                                .unis(
                                        doctors.stream()
                                                .map(doctor -> getPatientsByDoctorId(doctor.getUserId())
                                                        .onItem().transform(
                                                                patients -> new DoctorWithPatients(doctor, patients)
                                                        )
                                                ).collect(Collectors.toList())
                                )
                                .withUni(results -> Uni.createFrom().item((List<DoctorWithPatients>)results))
                );
    }

    public Uni<List<DoctorDto>> searchDoctors(String name){
        return doctorMSClient.searchDoctorsByName(name);
    }

    public Uni<List<PatientDto>> getPatientsByDoctorId(String doctorId) {
        return patientMSClient.getPatientsByDoctorId(doctorId);
    }

    public Uni<List<PatientDto>> searchPatientsByName(String name) {
        return patientMSClient.getPatientsByName(name);
    }

}