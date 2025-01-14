package com.fullstackdevops.messagingms.repository;

import com.fullstackdevops.messagingms.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findBySenderIdAndRecipientId(String senderId, String recipientId);

    List<Conversation> findBySenderIdOrRecipientId(String senderId, String recipientId);
}
