package com.fullstackdevops.patientms.repository;

import com.fullstackdevops.patientms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {


}
