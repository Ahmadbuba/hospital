package com.system.hospital.service;

import com.system.hospital.model.Patient;
import com.system.hospital.model.Person;
import com.system.hospital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Override
    public void createPatient(Person person) {
        Patient patient = new Patient();
        patient.setPerson(person);
        patientRepository.save(patient);
    }

    @Override
    public void updatePatientPerson(long id, Person person) {

    }
}
