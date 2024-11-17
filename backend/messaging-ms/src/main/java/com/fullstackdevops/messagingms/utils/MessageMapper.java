package com.fullstackdevops.messagingms.utils;

import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.model.Conversation;
import com.fullstackdevops.messagingms.model.Message;

public class MessageMapper {

    public static MessageDto toDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setConversationId(message.getConversation().getId());
        dto.setSenderId(message.getSenderId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }

    public static Message toEntity(MessageDto dto, Conversation conversation) {
        Message message = new Message();
        message.setConversation(conversation);
        message.setSenderId(dto.getSenderId());
        message.setContent(dto.getContent());
        message.setTimestamp(dto.getTimestamp());
        return message;
    }
}
