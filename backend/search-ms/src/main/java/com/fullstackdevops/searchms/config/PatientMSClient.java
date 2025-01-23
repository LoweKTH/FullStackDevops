package com.fullstackdevops.searchms.config;

import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PatientMSClient {
    @GET
    @Path("/{patientId}/doctorstaff")
    DoctorStaffDto getDoctorsForPatient(@PathParam("patientId") String patientId);

    @GET
    @Path("/diagnosis")
    Uni<List<PatientDto>> getPatientsByDiagnosis(@QueryParam("name") String diagnosisName);

    @GET
    @Path("/{doctorStaffId}/doctorstaffgetpatients")
    Uni<List<PatientDto>> getPatientsByDoctorId(@PathParam("doctorStaffId") String doctorId);

}
