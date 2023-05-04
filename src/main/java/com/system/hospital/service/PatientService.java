package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientResponse;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    void createPatient(PatientDto patientDto);

    List<PatientResponse> getAllPatients(Optional<String> firstName);

    PatientResponse getPatientById(long id);

    PatientResponse updatePatient(PatientDto patientDto, long id);

    void deletePatient(long id);
}
