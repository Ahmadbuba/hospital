package com.system.hospital.repository;

import java.util.List;

import com.system.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	List<Doctor> findByPatientRecordId(long patientRecordId);

}
