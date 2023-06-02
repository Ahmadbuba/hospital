package com.system.hospital.repository;


import com.system.hospital.model.Patient;
import com.system.hospital.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	List<Patient> findByPersonFirstName(String firstName);
	@Modifying
	@Query("UPDATE Patient p SET p.patientNextOfKinList = NULL WHERE p.patientNextOfKinList IS NOT NULL")
	void deleteAllPatientNextOfKinList();

}
