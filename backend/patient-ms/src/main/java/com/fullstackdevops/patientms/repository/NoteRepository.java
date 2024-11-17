package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("SELECT DISTINCT n.doctorId FROM Note n WHERE n.patient.id = :patientId")
    List<Long> findDistinctDoctorsByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT DISTINCT n.patient.id FROM Note n WHERE n.doctorId = :doctorId")
    List<Long> findDistinctPatientsByDoctorId(@Param("doctorId") Long doctorId);

}
