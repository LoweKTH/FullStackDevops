package com.fullstackdevops.doctorstaffms.repository;

import com.fullstackdevops.doctorstaffms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}