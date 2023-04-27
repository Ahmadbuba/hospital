package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.model.Gender;
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
    public void createPatient(PatientDto patientDto) {
        Person thePerson = Person.builder()
                .firstName(patientDto.firstName())
                .lastName(patientDto.lastName())
                .gender(patientDto.gender().equalsIgnoreCase("MALE")? Gender.MALE: Gender.FEMALE)
                .build();
        Patient thePatient = Patient.builder()
                .person(thePerson)
                .build();
        patientRepository.save(thePatient);
    }

    @Override
    public void updatePatientPerson(long id, Person person) {

    }
}
