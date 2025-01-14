package com.fullstackdevops.messagingms.service;

import com.fullstackdevops.messagingms.dto.ConversationDto;
import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.model.Conversation;

import java.util.List;

public interface MessagingService {
    Conversation createOrGetConversation(String patientId, String doctorId);
    MessageDto sendMessage(Long conversationId, String senderId, MessageDto messageDto);
    List<MessageDto> getMessages(Long conversationId);
    List<ConversationDto> getAllUserConversations(String userId);
}
