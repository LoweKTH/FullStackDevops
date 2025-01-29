package com.fullstackdevops.messagingms.service.implementation;

import com.fullstackdevops.messagingms.dto.ConversationDto;
import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.model.Conversation;
import com.fullstackdevops.messagingms.model.Message;
import com.fullstackdevops.messagingms.repository.ConversationRepository;
import com.fullstackdevops.messagingms.repository.MessageRepository;
import com.fullstackdevops.messagingms.service.MessagingService;
import com.fullstackdevops.messagingms.utils.MessageMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

                MessageDto messageDto = MessageMapper.toDto(message);
                messageDto.setSenderName(senderName);
                messageDtos.add(messageDto);
            } catch (Exception e) {

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

    private String getAdminToken() {
        String url = "https://fullstackkc.app.cloud.cbh.kth.se/realms/PatientSystem/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "admin-cli");
        formData.add("client_secret", "njx87Pe6ky6SWPMLLzGm4k9A2HVmCsxB");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        System.out.println(response);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }

        throw new RuntimeException("Failed to get admin token");
    }

    private String getUserUsername(String userId) {
        String url = "https://fullstackkc.app.cloud.cbh.kth.se/admin/realms/PatientSystem/users/" + userId;


        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String accessToken = getAdminToken();
        headers.setBearerAuth(accessToken);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);

        System.out.println(response);


        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> userData = response.getBody();

            return (String) userData.get("username");
        }

        throw new RuntimeException("Failed to get user by ID");
    }

}

