package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientResponseDto;
import com.system.hospital.model.Person;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    void createPatient(PatientDto patientDto);

    List<PatientResponseDto> getAllPatients(Optional<String> firstName);

    PatientResponseDto getPatientById(long id);

    PatientResponseDto updatePatient(PatientDto patientDto, long id);

    void deletePatient(long id);
}
