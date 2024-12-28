package com.fullstackdevops.messagingms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDto {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private Date createdAt;
}
