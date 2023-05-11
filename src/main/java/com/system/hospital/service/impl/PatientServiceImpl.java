package com.system.hospital.service.impl;

import com.system.hospital.dto.*;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.*;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.PatientService;
import com.system.hospital.service.UtilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;
    @Override
    public long createPatient(PatientDto patientDto) {
        Patient patient = UtilityService.convertPatientDtoToPatient(patientDto);
        patientRepository.save(patient);
        return patient.getId();
    }
    @Override
    public List<PatientResponse> getAllPatients(Optional<String> firstName) {
        List <Patient> thePatients = new ArrayList<>();
        List<PatientResponse> conversions = new ArrayList<>();
        if (!firstName.isPresent()) {
            thePatients = patientRepository.findAll();
        } else thePatients = patientRepository.findByPersonFirstName(firstName.get());

        if (!thePatients.isEmpty()) {
            conversions = thePatients.stream()
                    .map(patient -> UtilityService.convertFromPatientToPatientDto(patient))
                    .collect(Collectors.toList());
        }
        return conversions;

    }

    @Override
    public PatientResponse getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        return UtilityService.convertFromPatientToPatientDto(patient);
    }

    @Override
    public long updatePatient(PatientDto patientDto, long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        patient.updatePatient(patientDto);
        patientRepository.save(patient);
        return patient.getId();
    }

    @Override
    public void deletePatient(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        patientRepository.delete(patient);
    }

    private List<PatientResponse> getPatients() {
        List<PatientResponse> thePatients = new ArrayList<>();
        List <Patient> patients = patientRepository.findAll();
        if (!patients.isEmpty()) {
            thePatients = patients.stream().map(patient -> convertFromPatientToPatientDto(patient)).collect(Collectors.toList());
        }
        return thePatients;
    }

    static Gender getGenderEnum(String theGender) {
        return theGender.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE;
    }
    static String getGenderString(Gender theGender) {
        return theGender.toString();
    }
    private Person buildPerson (String firstName, String lastName, Gender gender) {
        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .build();
    }

    private Address buildAdress(Optional<AddressDto> addressDto) {
        if (addressDto.isPresent()) {
            AddressDto theAddressDto = addressDto.get();
            Address conversion = Address.builder()
                    .houseNumber(Optional.ofNullable(theAddressDto.houseNumber()).isPresent() ? theAddressDto.houseNumber() : null)
                    .street(Optional.ofNullable(theAddressDto.street()).isPresent() ? theAddressDto.street() : null)
                    .state(Optional.ofNullable(theAddressDto.state()).isPresent() ? theAddressDto.state(): null)
                    .build();
            return conversion;
        }
        return null;
    }

    private PersonalDetail buildPersonalDetail(Optional<PersonalDetailDto> personalDetailDto) {
        if(personalDetailDto.isPresent()) {
            PersonalDetailDto thePersonalDetail = personalDetailDto.get();
            PersonalDetail conversion = PersonalDetail.builder()
                    .weight(thePersonalDetail.weight())
                    .bloodGroup(thePersonalDetail.bloodGroup())
                    .genoType(thePersonalDetail.genoType())
                    .build();
            return conversion;
        }
        return null;
    }

    private PatientNextOfKin buildPatientNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
        PatientNextOfKin conversion = PatientNextOfKin.builder()
                .person(
                        buildPerson(
                                patientNextOfKinDto.first_name(),
                                patientNextOfKinDto.last_name(),
                                getGenderEnum(patientNextOfKinDto.gender())
                        )
                )
                .address(
                        buildAdress(
                                Optional.ofNullable(patientNextOfKinDto.address()
                                ))
                )
                .build();

        return conversion;
    }
    private List<PatientNextOfKin> returnNextOfKins(Optional<List<PatientNextOfKinDto>> nextOfKinDtos) {
        List<PatientNextOfKin> theNextOfKins = new ArrayList<>();
        if (nextOfKinDtos.isPresent()) {
            if (nextOfKinDtos.get().size() > 0) {
                theNextOfKins = nextOfKinDtos.get().stream()
                        .map(dto -> buildPatientNextOfKin(dto))
                        .collect(Collectors.toList());

            }
        }
        return theNextOfKins;
    }

    private PatientResponse convertFromPatientToPatientDto(Patient patient) {
        PatientResponse patientResponse = PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getPerson().getFirstName())
                .lastName(patient.getPerson().getLastName())
                .gender(getGenderString(patient.getPerson().getGender()))
                .address(buildAdressDto(Optional.ofNullable(patient.getAddress())))
                .details(buildPersonalDetailDto(Optional.ofNullable(patient.getPersonalDetail())))
                .build();
        return patientResponse;
    }

    private AddressDto buildAdressDto(Optional<Address> address) {
        if (address.isPresent()) {
            Address theAddress = address.get();
            AddressDto conversion = AddressDto.builder()
                    .houseNumber(theAddress.getHouseNumber())
                    .street(theAddress.getStreet())
                    .state(theAddress.getState())
                    .build();
            return conversion;
        }
        return null;
    }

    private PersonalDetailDto buildPersonalDetailDto(Optional<PersonalDetail> personalDetail) {
        if (personalDetail.isPresent()) {
            PersonalDetail thePersonalDetail = personalDetail.get();
            PersonalDetailDto conversion = PersonalDetailDto.builder()
                    .weight(thePersonalDetail.getWeight())
                    .bloodGroup(thePersonalDetail.getBloodGroup())
                    .genoType(thePersonalDetail.getGenoType())
                    .build();
            return conversion;
        }
        return null;
    }

    private List<PatientNextOfKinDto> returnNextOfKinsDto(Optional<List<PatientNextOfKin>> nextOfKins) {
        List<PatientNextOfKinDto> theNextOfKinDtos = new ArrayList<>();
        if (nextOfKins.isPresent()) {
            if (nextOfKins.get().size() > 0) {
                theNextOfKinDtos = nextOfKins.get().stream()
                        .map(nextOfKin -> buildPatientNextOfKinDto(nextOfKin))
                        .collect(Collectors.toList());

            }
        }
        return theNextOfKinDtos;
    }

    private PatientNextOfKinDto buildPatientNextOfKinDto(PatientNextOfKin patientNextOfKin) {
        PatientNextOfKinDto conversion = PatientNextOfKinDto.builder()
                .first_name(patientNextOfKin.getPerson().getFirstName())
                .last_name(patientNextOfKin.getPerson().getLastName())
                .gender(getGenderString(patientNextOfKin.getPerson().getGender()))
                .address(buildAdressDto(Optional.ofNullable(patientNextOfKin.getAddress())))
                .build();

        return conversion;
    }
}
