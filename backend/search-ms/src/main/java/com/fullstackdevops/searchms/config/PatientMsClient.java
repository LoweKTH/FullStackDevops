package com.fullstackdevops.searchms.config;

import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;


@RegisterRestClient
public interface PatientMsClient {

    @GET
    @Path("/{patientId}/doctorstaff")
    DoctorStaffDto getDoctorsForPatient(@PathParam("patientId") Long patientId);

    @GET
    @Path("/diagnosis")
    @Produces(MediaType.APPLICATION_JSON)
    List<PatientDto> getPatientsByDiagnosis(@QueryParam("name") String diagnosisName);
}
