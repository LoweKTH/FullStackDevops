package com.fullstackdevops.searchms.controller;

import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.service.SearchService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchController {

    @Inject
    SearchService searchService;

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
    public Response searchPatientsByDiagnosis(@QueryParam("diagnosis") String diagnosis) {
        try {
            List<PatientDto> patients = searchService.searchPatientsByDiagnosis(diagnosis);
            return Response.ok(patients).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred while searching for patients: " + e.getMessage())
                    .build();
        }
    }
}
