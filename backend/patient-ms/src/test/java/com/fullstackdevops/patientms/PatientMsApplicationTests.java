package com.fullstackdevops.patientms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackdevops.patientms.controller.PatientController;
import com.fullstackdevops.patientms.dto.PatientDto;
import com.fullstackdevops.patientms.service.PatientService;
import com.fullstackdevops.patientms.utils.SecurityConfig;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(PatientController.class)
class PatientMsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	@Autowired
	private ObjectMapper objectMapper;

	private PatientDto mockPatient;

	@BeforeEach
	void setUp() {
		// Initialize a mock patient object

		mockPatient = new PatientDto();
		mockPatient.setUserId("321423543rrefr");
		mockPatient.setFirstname("John");
		mockPatient.setLastname("Doe");
		mockPatient.setEmail("john.doe@example.com");
		mockPatient.setPhoneNumber("123-456-7890");
	}

	@Test
	void testCreatePatient() throws Exception {
		// Mock the service behavior
		when(patientService.createPatient(any(PatientDto.class))).thenReturn(mockPatient);

		// Perform the POST request and verify the response
		mockMvc.perform(post("/api/patients/addPatient")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(mockPatient))
						.with(csrf()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.userId").value(1L))
				.andExpect(jsonPath("$.firstname").value("John"))
				.andExpect(jsonPath("$.lastname").value("Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.phoneNumber").value("123-456-7890"));
	}
}
