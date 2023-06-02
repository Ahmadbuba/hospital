package com.system.hospital.repository;

import com.system.hospital.model.PatientNextOfKin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientNextOfKinRepository extends JpaRepository<PatientNextOfKin, Long> {

        void deleteAllByPatientId(long patientId);
}
