package com.fullstackdevops.searchms.controller;

import com.fullstackdevops.searchms.dto.DoctorDto;
import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.DoctorWithPatients;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.service.SearchService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
    }

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/{patientId}")
    public Response searchDoctorsForPatient(
            @PathParam("patientId") String patientId,
            @QueryParam("name") String doctorName) {

        if (doctorName == null || doctorName.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Doctor name query parameter is required.")
                    .build();
        }

        // Use JWT claims for logging/debugging
        String userId = jwt.getSubject();
        String role = jwt.getClaim("role");

        System.out.println("Request by User ID: " + userId + ", Role: " + role);

        DoctorStaffDto result = searchService.searchDoctorsForPatient(patientId, doctorName);
        return Response.ok(result).build();
    }

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PatientDto>> searchPatientsByDiagnosis(@QueryParam("diagnosis") String diagnosis) {
        // Log JWT claims for debugging
        System.out.println("JWT Claims: " + jwt.getClaimNames());
        System.out.println("User Preferred Username: " + jwt.getClaim("preferred_username"));
        return searchService.searchPatientsByDiagnosis(diagnosis);
    }

    @GET
    @Path("/searchDoctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<DoctorWithPatients>> searchDoctorsWithPatients(@QueryParam("name") String name) {
        String userId = jwt.getSubject();
        System.out.println("Searching Doctors for User: " + userId);
        return searchService.getDoctorsWithPatients(name);
    }

    @GET
    @Path("/hello")
    @RolesAllowed("DOCTOR")
    public Response helloWorld() {
        return Response.ok("Hello, world!").build();
    }
}
