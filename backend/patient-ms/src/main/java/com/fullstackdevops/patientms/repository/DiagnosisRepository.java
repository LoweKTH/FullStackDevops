package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Diagnosis;
import com.fullstackdevops.patientms.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {


    @Query("SELECT DISTINCT d.patientId FROM Diagnosis d WHERE d.doctorstaffId = :doctorstaffId")
    List<Long> findDistinctPatientsByDoctorstaffId(@Param("doctorstaffId") Long doctorstaffId);

    @Query("SELECT DISTINCT d.doctorstaffId FROM Diagnosis d WHERE d.patientId = :patientId")
    List<Long> findDistinctDoctorstaffByPatientId(@Param("patientId") Long patientId);

    List<Diagnosis> findByPatientId(Long patientId);

}
