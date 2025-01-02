package com.fullstackdevops.searchms.service;

import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.config.PatientServiceRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class PatientSearchService {

    @RestClient
    PatientServiceRestClient patientServiceRestClient;

    public List<PatientDto> searchPatients(String name, String condition) {
        return patientServiceRestClient.searchPatients(name, condition);
    }
}
