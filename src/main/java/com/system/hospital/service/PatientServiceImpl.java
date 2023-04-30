package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientResponseDto;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.Gender;
import com.system.hospital.model.Patient;
import com.system.hospital.model.Person;
import com.system.hospital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<PatientResponseDto> getAllPatients(Optional<String> firstName) {
        List<PatientResponseDto> allPatients = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        String theName = firstName.isPresent() ? firstName.get() : null;

        patients = Optional.ofNullable(theName)
                .filter(name -> !theName.isEmpty())
                .map(name -> patientRepository.findByPersonFirstName(name))
                .orElseGet(patientRepository::findAll);

        if (!patients.isEmpty()) {
            allPatients = patients.stream()
                    .map(patient -> convertToPatientResponseDto(patient))
                    .collect(Collectors.toList());
        }
        return allPatients;
    }

    @Override
    public PatientResponseDto getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));

        return convertToPatientResponseDto(patient);
    }

    @Override
    public PatientResponseDto updatePatient(PatientDto patientDto, long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        Patient updatedPatient = patientRepository.save(convertToPatientEntity(patientDto, id));
        return convertToPatientResponseDto(updatedPatient);
    }

    @Override
    public void deletePatient(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        patientRepository.delete(patient);
    }

    private Patient convertToPatientEntity(PatientDto patientDto, long id) {
        Patient thePatient = Patient.builder()
                .id(id)
                .person(Person.builder()
                        .firstName(patientDto.firstName())
                        .lastName(patientDto.lastName())
                        .gender(patientDto.gender().equalsIgnoreCase("Male")? Gender.MALE: Gender.FEMALE)
                        .build())
                .build();
        return thePatient;
    }

    private String convertEnumToString(Gender value) {
        String res = value.toString().toLowerCase();
        res = res.substring(0,1).toUpperCase() + res.substring(1);
        return res;
    }

    private PatientResponseDto convertToPatientResponseDto(Patient patient) {
        PatientResponseDto thePatient = PatientResponseDto.builder()
                .id(patient.getId())
                .firstName(patient.getPerson().getFirstName())
                .lastName(patient.getPerson().getLastName())
                .gender(convertEnumToString(patient.getPerson().getGender()))
                .build();
        return thePatient;
    }

}
