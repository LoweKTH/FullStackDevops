package com.fullstackdevops.searchms.config;

import com.fullstackdevops.searchms.dto.DoctorDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
@ApplicationScoped
public interface DoctorMSClient {

    @GET
    @Path("/searchDoctors")
    Uni<List<DoctorDto>> searchDoctorsByName(@QueryParam("name") String name);
}