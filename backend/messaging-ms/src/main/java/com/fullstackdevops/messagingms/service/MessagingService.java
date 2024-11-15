package com.fullstackdevops.messagingms.service;

import com.fullstackdevops.messagingms.dto.ConversationDto;
import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.model.Conversation;

import java.util.List;

public interface MessagingService {
    Conversation createOrGetConversation(Long patientId, Long doctorId);
    MessageDto sendMessage(Long conversationId, Long senderId, MessageDto messageDto);
    List<MessageDto> getMessages(Long conversationId);
    List<ConversationDto> getAllUserConversations(Long userId);
}
