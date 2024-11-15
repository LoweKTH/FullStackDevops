package com.fullstackdevops.messagingms.controller;


import com.fullstackdevops.messagingms.dto.MessageDto;
import com.fullstackdevops.messagingms.model.Conversation;
import com.fullstackdevops.messagingms.service.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
@CrossOrigin("*")
public class MessagingController {
    private final MessagingService messagingService;

    @PostMapping("/conversation")
    public ResponseEntity<Conversation> createConversation(@RequestParam Long senderId, @RequestParam Long recipientId) {
        Conversation conversation = messagingService.createOrGetConversation(senderId, recipientId);
        return ResponseEntity.ok(conversation);
    }


    @PostMapping("/{conversationId}/send")
    public ResponseEntity<MessageDto> sendMessage(@PathVariable Long conversationId, @RequestBody MessageDto messageDto) {
        MessageDto message = messagingService.sendMessage(conversationId, messageDto.getSenderId(), messageDto);
        return ResponseEntity.ok(message);
    }


    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable Long conversationId) {
        List<MessageDto> messages = messagingService.getMessages(conversationId);
        return ResponseEntity.ok(messages);
    }
}

