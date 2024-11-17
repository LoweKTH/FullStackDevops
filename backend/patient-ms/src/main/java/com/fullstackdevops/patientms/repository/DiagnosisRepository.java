package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

}
