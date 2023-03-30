package com.system.hospital.repository;


import com.system.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	List<Patient> findByPersonFirstNameContaining(String firstName);
}
