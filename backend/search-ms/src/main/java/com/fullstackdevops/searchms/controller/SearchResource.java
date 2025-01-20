package com.fullstackdevops.searchms.controller;

import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.service.SearchService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/search")
public class SearchResource {

    private final SearchService searchService;

    public SearchResource(SearchService searchService) {this.searchService = searchService;}



    @GET
    @Path("/{patientId}")
    public Response searchDoctorsForPatient(
            @PathParam("patientId") Long patientId,
            @QueryParam("name") String doctorName) {

        if (doctorName == null || doctorName.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Doctor name query parameter is required.")
                    .build();
        }

        DoctorStaffDto result = searchService.searchDoctorsForPatient(patientId, doctorName);

        return Response.ok(result).build();
    }

    @GET
    @Path("/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PatientDto>> searchPatientsByDiagnosis(@QueryParam("diagnosis") String diagnosis) {
        return searchService.searchPatientsByDiagnosis(diagnosis);
    }
}
