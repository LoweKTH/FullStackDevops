package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Diagnosis;
import com.fullstackdevops.patientms.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, String> {


    @Query("SELECT DISTINCT d.patientId FROM Diagnosis d WHERE d.doctorstaffId = :doctorstaffId")
    List<String> findDistinctPatientsByDoctorstaffId(@Param("doctorstaffId") String doctorstaffId);

    @Query("SELECT DISTINCT d.doctorstaffId FROM Diagnosis d WHERE d.patientId = :patientId")
    List<String> findDistinctDoctorstaffByPatientId(@Param("patientId") String patientId);

    List<Diagnosis> findByPatientId(String patientId);

}
