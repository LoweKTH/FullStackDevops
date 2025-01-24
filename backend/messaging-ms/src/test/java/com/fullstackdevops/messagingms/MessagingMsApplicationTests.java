package com.fullstackdevops.messagingms;

import com.fullstackdevops.messagingms.controller.MessagingController;
import com.fullstackdevops.messagingms.model.Conversation;
import com.fullstackdevops.messagingms.service.MessagingService;
import com.fullstackdevops.messagingms.utils.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Import(SecurityConfig.class)
@WebMvcTest(MessagingController.class)
public class MessagingMsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessagingService messagingService;

	private Conversation mockConversation;

	@BeforeEach
	public void setup() {
		// Mock data for the conversation
		mockConversation = new Conversation();
		mockConversation.setId(1L);
		mockConversation.setSenderId("feweuhfi1edasd");
		mockConversation.setRecipientId("12eeih2eihde2i");
	}

	@Test
	public void testCreateConversation() throws Exception {
		// Mock the service to return the mockConversation
		when(messagingService.createOrGetConversation("feweuhfi1edasd", "12eeih2eihde2i")).thenReturn(mockConversation);

		// Perform the POST request to create a conversation
		mockMvc.perform(post("/api/messages/conversation")
						.param("senderId", "feweuhfi1edasd")
						.param("recipientId", "12eeih2eihde2i")
						.contentType(MediaType.APPLICATION_JSON)
						.with(jwt().authorities(() -> "ROLE_PATIENT"))
						.with(csrf()))
				.andExpect(status().isOk())  // Expect HTTP 200 OK
				.andExpect(jsonPath("$.id").value(1L))  // Check the conversation ID
				.andExpect(jsonPath("$.senderId").value("feweuhfi1edasd"))  // Check senderId
				.andExpect(jsonPath("$.recipientId").value("12eeih2eihde2i"));  // Check recipientId
	}
}
