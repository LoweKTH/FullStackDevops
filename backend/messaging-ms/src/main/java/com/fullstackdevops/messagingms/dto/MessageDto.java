package com.fullstackdevops.messagingms.dto;

import com.fullstackdevops.messagingms.model.Conversation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long id;
    private Long conversationId;
    private Long senderId;
    private String senderName;
    private String content;
    private Date timestamp = new Date();
}
