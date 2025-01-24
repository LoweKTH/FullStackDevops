package com.fullstackdevops.doctorstaffms.repository;

import com.fullstackdevops.doctorstaffms.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Optional<Doctor> findByUserId(String userId);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstname) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(d.lastname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameContainingIgnoreCase(@Param("name") String name);

}
