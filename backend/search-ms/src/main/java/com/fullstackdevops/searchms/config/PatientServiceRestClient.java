package com.fullstackdevops.searchms.config;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

import com.fullstackdevops.searchms.dto.PatientDto;

@RegisterRestClient(baseUri = "http://localhost:8082/api/patients")
public interface PatientServiceRestClient {

    @GET
    List<PatientDto> searchPatients(@QueryParam("name") String name, @QueryParam("condition") String condition);
}
