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
		mockConversation.setSenderId(1L);
		mockConversation.setRecipientId(2L);
	}

	@Test
	public void testCreateConversation() throws Exception {
		// Mock the service to return the mockConversation
		when(messagingService.createOrGetConversation(1L, 2L)).thenReturn(mockConversation);

		// Perform the POST request to create a conversation
		mockMvc.perform(post("/api/messages/conversation")
						.param("senderId", "1")
						.param("recipientId", "2")
						.contentType(MediaType.APPLICATION_JSON)
						.with(csrf()))
				.andExpect(status().isOk())  // Expect HTTP 200 OK
				.andExpect(jsonPath("$.id").value(1L))  // Check the conversation ID
				.andExpect(jsonPath("$.senderId").value(1L))  // Check senderId
				.andExpect(jsonPath("$.recipientId").value(2L));  // Check recipientId
	}
}
