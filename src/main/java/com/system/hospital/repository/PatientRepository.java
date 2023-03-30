package com.system.hospital.repository;


import com.system.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	Patient findByPatientRecordId(long recordId);
	
	Patient findByPatientPersonalDetailsId(long patientPersonalDetailsId);
	
	List<Patient> findByPersonFirstNameContaining(String firstName);
}
