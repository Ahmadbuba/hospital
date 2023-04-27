package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.model.Person;

public interface PatientService {
    public void createPatient(PatientDto patientDto);

    public void updatePatientPerson(long id, Person person);
}
