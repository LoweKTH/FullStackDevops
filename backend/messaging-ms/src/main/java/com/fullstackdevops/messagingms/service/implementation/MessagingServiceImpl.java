package com.fullstackdevops.messagingms.service.implementation;

import com.fullstackdevops.messagingms.dto.ConversationDto;
import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.dto.UserDto;
import com.fullstackdevops.messagingms.model.Message;
import com.fullstackdevops.messagingms.repository.ConversationRepository;
import com.fullstackdevops.messagingms.repository.MessageRepository;
import com.fullstackdevops.messagingms.service.MessagingService;
import com.fullstackdevops.messagingms.model.Conversation;
import com.fullstackdevops.messagingms.utils.MessageMapper;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;



    @Override
    public Conversation createOrGetConversation(String senderId, String recipientId) {
        String normalizedSenderId = senderId.compareTo(recipientId) < 0 ? senderId : recipientId;
        String normalizedRecipientId = senderId.compareTo(recipientId) < 0 ? recipientId : senderId;

        return conversationRepository.findBySenderIdAndRecipientId(normalizedSenderId, normalizedRecipientId)
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.setSenderId(normalizedSenderId);
                    conversation.setRecipientId(normalizedRecipientId);
                    return conversationRepository.save(conversation);
                });
    }

    @Override
    public MessageDto sendMessage(Long conversationId, String senderId, MessageDto messageDto) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));


        Message message = MessageMapper.toEntity(messageDto, conversation);

        message = messageRepository.save(message);
        return MessageMapper.toDto(message);
    }

    @Override
    public List<MessageDto> getMessages(Long conversationId) {
        List<Message> messages = messageRepository.findByConversationIdOrderByTimestamp(conversationId);
        List<MessageDto> messageDtos = new ArrayList<>();

        String token = getAdminToken();
        System.out.println("token:              "+ token);
        for (Message message : messages) {
            try {


                String senderName = getUserUsername(message.getSenderId());

                // Map the Message entity to a DTO
                MessageDto messageDto = MessageMapper.toDto(message);
                messageDto.setSenderName(senderName);
                messageDtos.add(messageDto);
            } catch (Exception e) {
                // Handle exceptions (e.g., user not found in Keycloak)
                e.printStackTrace();
            }
        }

        return messageDtos;
    }

    @Override
    public List<ConversationDto> getAllUserConversations(String userId) {
        List<Conversation> conversations = conversationRepository.findBySenderIdOrRecipientId(userId, userId);
        List<ConversationDto> conversationDtos = new ArrayList<>();

        for (Conversation conversation : conversations) {
            ConversationDto conversationDto = new ConversationDto(
                    conversation.getId(),
                    conversation.getSenderId(),
                    conversation.getRecipientId(),
                    conversation.getCreatedAt()
            );
            conversationDtos.add(conversationDto);
        }

        return conversationDtos;
    }



    private String getJwtTokenFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getTokenValue();
        }
        return null;
    }







    private String getAdminToken() {
        String url = "http://keycloak:8080/realms/PatientSystem/protocol/openid-connect/token";

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Prepare form data (MultiValueMap)
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "admin-cli");
        formData.add("client_secret", "s7BbMv5X7wbjezYob7Bc9wV36X9DHFMP");
        System.out.println(formData.get("client_secret"));
        System.out.println(formData.toString());
        // Create the request entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        // Send POST request
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        System.out.println(response);
        // Parse the response and return the token
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("Failed to get admin token");
    }

    private String getUserUsername(String userId) {
        String url = "http://keycloak:8080/admin/realms/PatientSystem/users/" + userId;

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Add the access token to the Authorization header
        String accessToken = getAdminToken();  // Assuming getAdminToken() works fine
        headers.setBearerAuth(accessToken);

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send GET request
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);

        System.out.println(response);

        // Parse the response and extract the username
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> userData = response.getBody();
            // Assuming 'username' is the key in the response JSON
            return (String) userData.get("username");
        }

        throw new RuntimeException("Failed to get user by ID");
    }

}

