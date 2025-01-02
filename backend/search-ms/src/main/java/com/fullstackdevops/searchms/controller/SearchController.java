package com.fullstackdevops.searchms.controller;

import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.service.PatientSearchService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.List;

@Path("/api/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class    SearchController {

    @Inject
    PatientSearchService patientSearchService;

    @GET
    @Path("/patients")
    public Response searchPatients(
            @QueryParam("name") String name,
            @QueryParam("condition") String condition) {
        List<PatientDto> patients = patientSearchService.searchPatients(name, condition);
        return Response.ok(patients).build();
    }
}
