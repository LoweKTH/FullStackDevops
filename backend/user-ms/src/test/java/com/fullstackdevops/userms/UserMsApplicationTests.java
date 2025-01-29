package com.fullstackdevops.userms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstackdevops.userms.controller.UserController;
import com.fullstackdevops.userms.dto.LoginDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserMsApplicationTests {

	@MockBean
	private UserService userService;

	@Autowired
	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	void testGetUserById() throws Exception {
		Long userId = 1L;
		UserDto userDto = new UserDto(userId, "test@example.com", "testuser", "password", "ROLE_USER");

		when(userService.getUserById(userId)).thenReturn(userDto);

		mockMvc.perform(get("/api/user/{id}", userId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(userId))
				.andExpect(jsonPath("$.username").value("testuser"))
				.andExpect(jsonPath("$.email").value("test@example.com"));

		verify(userService, times(1)).getUserById(userId);
	}

	@Test
	void testLogin() throws Exception {

		String username = "testuser";
		String password = "password";
		LoginDto loginDto = new LoginDto(username, password);


		UserDto userDto = new UserDto(1L, "test@example.com", "testuser", "password", "ROLE_USER");
		when(userService.login(username, password)).thenReturn(userDto);


		mockMvc.perform(post("/api/user/login")
						.contentType("application/json")
						.content("{\"username\":\"testuser\",\"password\":\"password\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.username").value("testuser"))
				.andExpect(jsonPath("$.email").value("test@example.com"));

		verify(userService, times(1)).login(username, password);
	}
}
