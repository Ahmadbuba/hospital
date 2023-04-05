package com.system.hospital.service;

import com.system.hospital.model.Person;

public interface PatientService {
    public void createPatient(Person person);

    public void updatePatientPerson(long id, Person person);
}
