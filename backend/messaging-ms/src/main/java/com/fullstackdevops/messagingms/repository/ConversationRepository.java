package com.fullstackdevops.messagingms.repository;


import com.fullstackdevops.messagingms.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByPatientIdAndDoctorId(Long patientId, Long doctorId);
}

