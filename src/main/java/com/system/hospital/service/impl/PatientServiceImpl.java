package com.system.hospital.service.impl;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.NextOfKinDto;
import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientResponse;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.*;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Override
    public void createPatient(PatientDto patientDto) {
        Person thePerson = Person.builder()
                .firstName(patientDto.first_name())
                .lastName(patientDto.last_name())
                .gender(patientDto.gender().equalsIgnoreCase("MALE")? Gender.MALE: Gender.FEMALE)
                .build();
        Patient thePatient = Patient.builder()
                .person(thePerson)
                .build();
        patientRepository.save(thePatient);

        patientDto.next_of_kins().stream().map()

        Address address = Address.builder()
                .state(patientDto.address().state())
                .houseNumber(patientDto.address().houseNumber())
                .street(patientDto.address().street())
                .build();
        PersonalDetail  personalDetail = PersonalDetail.builder()
                .bloodGroup(patientDto.personal_details().bloodGroup())
                .weight(patientDto.personal_details().weight())
                .genoType(patientDto.personal_details().genoType())
                .build();
        PatientNextOfKin patientNextOfKin = PatientNextOfKin.builder()
                .patient(patientDto)
                .build();
        HealthRecord healthRecord;

    }


    @Override
    public List<PatientResponse> getAllPatients(Optional<String> firstName) {
        List<PatientResponse> allPatients = new ArrayList<>();
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
    public PatientResponse getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));

        return convertToPatientResponseDto(patient);
    }

    @Override
    public PatientResponse updatePatient(PatientDto patientDto, long id) {
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
                        .firstName(patientDto.first_name())
                        .lastName(patientDto.last_name())
                        .gender(getGender(patientDto.gender()))
                        .build())
                .build();
        return thePatient;
    }



    private String convertEnumToString(Gender value) {
        String res = value.toString().toLowerCase();
        res = res.substring(0,1).toUpperCase() + res.substring(1);
        return res;
    }

    private static Gender getGender(String patientDto) {
        return patientDto.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE;
    }
    private PatientResponse convertToPatientResponseDto(Patient patient) {
        PatientResponse thePatient = PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getPerson().getFirstName())
                .lastName(patient.getPerson().getLastName())
                .gender(convertEnumToString(patient.getPerson().getGender()))
                .build();
        return thePatient;
    }

    private PatientNextOfKin convertFromDtoToPatientNextOfKin(NextOfKinDto nextOfKinDto) {
        PatientNextOfKin theNextOfKin = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName(nextOfKinDto.first_name())
                        .lastName(nextOfKinDto.last_name())
                        .gender(nextOfKinDto.gender())
                        .build()
                )
                .build();
    }

}
