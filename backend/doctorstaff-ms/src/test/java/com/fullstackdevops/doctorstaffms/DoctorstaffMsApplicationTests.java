package com.fullstackdevops.doctorstaffms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackdevops.doctorstaffms.controller.DoctorController;
import com.fullstackdevops.doctorstaffms.dto.DoctorDto;
import com.fullstackdevops.doctorstaffms.service.DoctorService;
import com.fullstackdevops.doctorstaffms.utils.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(DoctorController.class)
class DoctorstaffMsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    private DoctorDto mockDoctor;

    @BeforeEach
    void setUp() {
        // Initialize a mock doctor object
        mockDoctor = new DoctorDto();
        mockDoctor.setUserId("324234ewfafsdfda");
        mockDoctor.setFirstname("John");
        mockDoctor.setLastname("Doe");
        mockDoctor.setSpecialty("Cardiology");
        mockDoctor.setEmail("dr.john.doe@example.com");
        mockDoctor.setPhoneNumber("987-654-3210");
    }

    @Test
    void testCreateDoctor() throws Exception {
        // Mock the service behavior
        when(doctorService.createDoctor(any(DoctorDto.class))).thenReturn(mockDoctor);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/doctors/addDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDoctor))
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.specialty").value("Cardiology"))
                .andExpect(jsonPath("$.email").value("dr.john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("987-654-3210"));
    }

    @Test
    void testGetDoctorById() throws Exception {
        // Mock the service behavior
        when(doctorService.getDoctorById("324234ewfafsdfda")).thenReturn(mockDoctor);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.specialty").value("Cardiology"))
                .andExpect(jsonPath("$.email").value("dr.john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("987-654-3210"));
    }

}
