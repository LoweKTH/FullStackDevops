package com.fullstackdevops.doctorstaffms.repository;

import com.fullstackdevops.doctorstaffms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUserId(Long userId);
}