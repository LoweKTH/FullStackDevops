package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("SELECT p FROM Patient p WHERE p.userId = :userId")
    Optional<Patient> findByUserId(@Param("userId")String userId);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstname) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(p.lastname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByNameContainingIgnoreCase(@Param("name") String name);

}
