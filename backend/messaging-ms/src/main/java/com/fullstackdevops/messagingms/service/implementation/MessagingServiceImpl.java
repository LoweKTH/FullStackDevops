package com.fullstackdevops.messagingms.service.implementation;

import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.model.Message;
import com.fullstackdevops.messagingms.repository.ConversationRepository;
import com.fullstackdevops.messagingms.repository.MessageRepository;
import com.fullstackdevops.messagingms.service.MessagingService;
import com.fullstackdevops.messagingms.model.Conversation;
import com.fullstackdevops.messagingms.utils.MessageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @Override
    public Conversation createOrGetConversation(Long senderId, Long recipientId) {
        return conversationRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.setSenderId(senderId);
                    conversation.setRecipientId(recipientId);
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
            MessageDto messageDto = MessageMapper.toDto(message);
            messageDtos.add(messageDto);
        }
        return messageDtos;
    }



}

