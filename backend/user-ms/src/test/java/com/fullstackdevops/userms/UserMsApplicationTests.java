package com.fullstackdevops.userms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackdevops.userms.controller.UserController;
import com.fullstackdevops.userms.dto.UserDto;
import com.fullstackdevops.userms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // Use @WebMvcTest for controller testing
public class UserMsApplicationTests {

	@MockBean
	private UserService userService;

	@Autowired
	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		// Initialize mocks
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Manually setup MockMvc
	}

	@Test
	void testGetUserById() throws Exception {
		// Arrange
		Long userId = 1L;
		UserDto userDto = new UserDto(userId, "test@example.com", "testuser", "password", "ROLE_USER");

		// Mock the service call
		when(userService.getUserById(userId)).thenReturn(userDto);

		// Act & Assert
		mockMvc.perform(get("/api/user/{id}", userId)) // Perform GET request
				.andExpect(status().isOk()) // Expect 200 OK status
				.andExpect(jsonPath("$.id").value(userId)) // Check if the ID in the response matches
				.andExpect(jsonPath("$.username").value("testuser")) // Check if the username in the response matches
				.andExpect(jsonPath("$.email").value("test@example.com")); // Check if the email in the response matches

		// Verify that the service method was called once
		verify(userService, times(1)).getUserById(userId);
	}
}
