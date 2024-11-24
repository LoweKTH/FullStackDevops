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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;

    @Override
    public Conversation createOrGetConversation(Long senderId, Long recipientId) {
        Long normalizedSenderId = Math.min(senderId, recipientId);
        Long normalizedRecipientId = Math.max(senderId, recipientId);

        return conversationRepository.findBySenderIdAndRecipientId(normalizedSenderId, normalizedRecipientId)
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.setSenderId(normalizedSenderId);
                    conversation.setRecipientId(normalizedRecipientId);
                    return conversationRepository.save(conversation);
                });
    }

    @Override
    public MessageDto sendMessage(Long conversationId, Long senderId, MessageDto messageDto) {
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


        for (Message message : messages) {
            UserDto user = restTemplate.getForObject("http://user-ms:8080/api/user/"+message.getSenderId() , UserDto.class);
            String senderName =user.getUsername();

            MessageDto messageDto = MessageMapper.toDto(message);
            messageDto.setSenderName(senderName);
            messageDtos.add(messageDto);
        }
        return messageDtos;
    }

    @Override
    public List<ConversationDto> getAllUserConversations(Long userId) {
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



}

