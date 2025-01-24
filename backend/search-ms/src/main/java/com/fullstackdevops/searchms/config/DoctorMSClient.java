package com.fullstackdevops.searchms.config;

import com.fullstackdevops.searchms.dto.DoctorDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.utils.JwtTokenHolder;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
@ApplicationScoped
public interface DoctorMSClient {

    @GET
    @Path("/searchDoctors")
    @ClientHeaderParam(name = "Authorization", value = "{generateAuthorizationHeader}")
    Uni<List<DoctorDto>> searchDoctorsByName(@QueryParam("name") String name);

    @GET
    @Path("/{doctorId}/patients")  // Updated to match the correct endpoint
    @ClientHeaderParam(name = "Authorization", value = "{generateAuthorizationHeader}")
    Uni<List<PatientDto>> getPatientsByDoctorId(@PathParam("doctorId") String doctorId);


    // Static method for token header generation
    default String generateAuthorizationHeader() {
        return "Bearer " + JwtTokenHolder.getToken();
    }
}