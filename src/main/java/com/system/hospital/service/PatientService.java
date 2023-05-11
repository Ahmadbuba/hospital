package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientResponse;
import com.system.hospital.model.Gender;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    long createPatient(PatientDto patientDto);

    List<PatientResponse> getAllPatients(Optional<String> firstName);

    PatientResponse getPatientById(long id);

    long updatePatient(PatientDto patientDto, long id);

    void deletePatient(long id);

    static Gender getGenderEnum(String gender) {
        return null;
    }
}
