package com.system.hospital.service;

import com.system.hospital.dto.*;
import com.system.hospital.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilityService {

    public static Gender getGenderEnum(String theGender) {
            return theGender.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE;
    }

   public static String getGenderString(Gender theGender) {
        return theGender.toString();
    }
    public static String getString(Optional<String> theString) {
        return theString.isPresent() ? theString.get() : null;
    }

    public static PersonDto buildPersonDto(PatientDto patientDto) {
       return PersonDto.builder()
                .firstName(patientDto.first_name())
                .lastName(UtilityService.getString(Optional.ofNullable(patientDto.last_name())))
                .gender(UtilityService.getGenderEnum(patientDto.gender()))
                .build();
    }

    public static PatientNextOfKin convertToNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
        Address theAddress = Optional.ofNullable(patientNextOfKinDto.address())
                .map(pNXKAddress -> buildAdress(Optional.ofNullable(patientNextOfKinDto.address())))
                .orElse(null);
        PersonDto personDto = PersonDto.builder()
                .firstName(patientNextOfKinDto.first_name())
                .lastName(getString(Optional.ofNullable(patientNextOfKinDto.last_name())))
                .gender(getGenderEnum(patientNextOfKinDto.gender()))
                .build();
        PatientNextOfKin theKin = PatientNextOfKin.builder()
                .address(theAddress)
                .person(Person.builder()
                        .firstName(personDto.firstName())
                        .lastName(personDto.lastName())
                        .gender(personDto.gender())
                        .build())
                .build();
        return theKin;
    }

    public static Address buildAdress(Optional<AddressDto> addressDto) {
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

    public static Patient convertPatientDtoToPatient(PatientDto patientDto) {
        Person thePerson = buildPerson(
                patientDto.first_name(),
                getString(Optional.ofNullable(patientDto.last_name())),
                getGenderEnum(patientDto.gender())
        );

        Address theAddress = buildAdress(
                Optional.ofNullable(patientDto.address())
        );

        PersonalDetail thePersonalDetail = buildPersonalDetail(
                Optional.ofNullable(patientDto.personal_details())
        );

        List<PatientNextOfKin> theNextOfKins = returnNextOfKins(
                Optional.ofNullable(patientDto.next_of_kins())
        );
        Patient thePatient = Patient.builder()
                .person(thePerson)
                .address(theAddress)
                .personalDetail(thePersonalDetail)
                .patientNextOfKinList(theNextOfKins)
                .build();
        return thePatient;
    }

    public static Person buildPerson (String firstName, String lastName, Gender gender) {
        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .build();
    }

    public static PersonalDetail buildPersonalDetail(Optional<PersonalDetailDto> personalDetailDto) {
        if(personalDetailDto.isPresent()) {
            PersonalDetailDto thePersonalDetail = personalDetailDto.get();
            PersonalDetail conversion = PersonalDetail.builder()
                    .weight(thePersonalDetail.weight())
                    .bloodGroup(getString(Optional.ofNullable(thePersonalDetail.bloodGroup())))
                    .genoType(getString(Optional.ofNullable(thePersonalDetail.genoType())))
                    .build();
            return conversion;
        }
        return null;
    }

    public static PatientNextOfKin buildPatientNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
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
    public static List<PatientNextOfKin> returnNextOfKins(Optional<List<PatientNextOfKinDto>> nextOfKinDtos) {
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

    public static PatientResponse convertFromPatientToPatientDto(Patient patient) {
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

    public static AddressDto buildAdressDto(Optional<Address> address) {
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

    public static PersonalDetailDto buildPersonalDetailDto(Optional<PersonalDetail> personalDetail) {
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

    public static List<PatientNextOfKinDto> returnNextOfKinsDto(Optional<List<PatientNextOfKin>> nextOfKins) {
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

    public static PatientNextOfKinDto buildPatientNextOfKinDto(PatientNextOfKin patientNextOfKin) {
        PatientNextOfKinDto conversion = PatientNextOfKinDto.builder()
                .first_name(patientNextOfKin.getPerson().getFirstName())
                .last_name(patientNextOfKin.getPerson().getLastName())
                .gender(getGenderString(patientNextOfKin.getPerson().getGender()))
                .address(buildAdressDto(Optional.ofNullable(patientNextOfKin.getAddress())))
                .build();

        return conversion;
    }

}
