package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.userId = :userId")
    Optional<Patient> findByUserId(@Param("userId")Long userId);

}
