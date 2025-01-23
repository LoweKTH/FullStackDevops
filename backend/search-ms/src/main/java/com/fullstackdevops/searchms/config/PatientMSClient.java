package com.fullstackdevops.searchms.config;

import com.fullstackdevops.searchms.dto.DoctorStaffDto;
import com.fullstackdevops.searchms.dto.PatientDto;
import com.fullstackdevops.searchms.utils.JwtTokenHolder;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PatientMSClient {

    @GET
    @Path("/{patientId}/doctorstaff")
    @ClientHeaderParam(name = "Authorization", value = "{generateAuthorizationHeader}")
    DoctorStaffDto getDoctorsForPatient(@PathParam("patientId") String patientId);

    @GET
    @Path("/diagnosis")
    @ClientHeaderParam(name = "Authorization", value = "{generateAuthorizationHeader}")
    Uni<List<PatientDto>> getPatientsByDiagnosis(@QueryParam("name") String diagnosisName);

    @GET
    @Path("/{doctorStaffId}/doctorstaffgetpatients")
    @ClientHeaderParam(name = "Authorization", value = "{generateAuthorizationHeader}")
    Uni<List<PatientDto>> getPatientsByDoctorId(@PathParam("doctorStaffId") String doctorId);

    // Static method for token header generation
    default String generateAuthorizationHeader() {
        return "Bearer " + JwtTokenHolder.getToken();
    }
}
