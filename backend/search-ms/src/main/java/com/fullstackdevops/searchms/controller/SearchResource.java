package com.fullstackdevops.searchms.controller;

import com.fullstackdevops.searchms.dto.DoctorDto;
import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.DoctorWithPatients;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.service.SearchService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/api/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final SearchService searchService;

    @Inject
    JsonWebToken jwt; // Inject JsonWebToken

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
    }

    @GET
    @PermitAll
    @Path("/{patientId}")
    public Response searchDoctorsForPatient(
            @PathParam("patientId") String patientId,
            @QueryParam("name") String doctorName) {

        if (doctorName == null || doctorName.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Doctor name query parameter is required.")
                    .build();
        }

        // Access JWT claims (example)
        String username = jwt.getName(); // Get the username
        System.out.println("User accessing searchDoctorsForPatient: " + username);

        DoctorStaffDto result = searchService.searchDoctorsForPatient(patientId, doctorName);

        return Response.ok(result).build();
    }

    @GET
    @PermitAll
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PatientDto>> searchPatientsByDiagnosis(@QueryParam("diagnosis") String diagnosis) {
        // Access JWT claims (example)
        String username = jwt.getName(); // Get the username
        System.out.println("User accessing searchPatientsByDiagnosis: " + username);
        return searchService.searchPatientsByDiagnosis(diagnosis);
    }

    @GET
    @PermitAll
    @Path("/searchDoctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<DoctorWithPatients>> searchDoctorsWithPatients(@QueryParam("name") String name) {
        // Access JWT claims (example)
        String username = jwt.getName(); // Get the username
        System.out.println("User accessing searchDoctorsWithPatients: " + username);
        return searchService.getDoctorsWithPatients(name);
    }
}