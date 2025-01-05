package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    @Query("SELECT DISTINCT n.doctorstaffId FROM Note n WHERE n.patientId = :patientId")
    List<String> findDistinctDoctorstaffByPatientId(@Param("patientId") String patientId);




    @Query("SELECT DISTINCT n.patientId FROM Note n WHERE n.doctorstaffId = :doctorstaffId")
    List<String> findDistinctPatientsByDoctorstaffId(@Param("doctorstaffId") String doctorstaffId);

    List<Note> findByPatientId(String patientId);


}
