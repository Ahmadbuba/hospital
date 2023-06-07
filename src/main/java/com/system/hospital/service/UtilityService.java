package com.system.hospital.service;

import com.system.hospital.dto.*;
import com.system.hospital.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilityService {

    public Gender getGenderEnum(String theGender) {
            return theGender.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE;
    }

   public String getGenderString(Gender theGender) {
        return theGender.toString();
    }
    public String getString(Optional<String> theString) {
        return theString.isPresent() ? theString.get() : null;
    }

    public Patient convertPatientDtoToPatient(PatientDto patientDto) {
        Person thePerson = buildPerson(
                patientDto.first_name(),
                Optional.ofNullable(patientDto.last_name()),
                patientDto.gender()
        );

        Address theAddress = addressBuilder(Optional.ofNullable(patientDto.address()));

        PersonalDetail thePersonalDetail = personalDetailBuilder(Optional.ofNullable(patientDto.personal_details()));

        List<PatientNextOfKin> theNextOfKins = buildPatientNextOfKinList(Optional.ofNullable(patientDto.next_of_kins()));
        Patient thePatient = Patient.builder()
                .person(thePerson)
                .address(theAddress)
                .personalDetail(thePersonalDetail)
                .build();
        thePatient.setNextOfKins(theNextOfKins);
        return thePatient;
    }

    public PatientResponse convertFromPatientToPatientResponse(Patient patient) {
        PatientResponse patientResponse = PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getPerson().getFirstName())
                .lastName(patient.getPerson().getLastName())
                .gender(getGenderString(patient.getPerson().getGender()))
                .address(buildAddressDto(Optional.ofNullable(patient.getAddress())))
                .details(buildPersonalDetailDto(Optional.ofNullable(patient.getPersonalDetail())))
                .next_of_kins(returnNextOfKinsResponse(Optional.ofNullable(patient.getPatientNextOfKinList())))
                .build();
        return patientResponse;
    }
    public Address buildAddress(AddressDto addressDto) {
            Address conversion = Address.builder()
                    .houseNumber(Optional.ofNullable(addressDto.houseNumber()).orElse(0))
                    .street(Optional.ofNullable(addressDto.street()).orElse(null))
                    .state(Optional.ofNullable(addressDto.state()).orElse(null))
                    .build();
            return conversion;
    }

    public AddressDto buildAddressDto(Optional<Address> address) {
//        if (address.isPresent()) {
//            Address theAddress = address.get();
//            AddressDto conversion = AddressDto.builder()
//                    .houseNumber(theAddress.getHouseNumber())
//                    .street(theAddress.getStreet())
//                    .state(theAddress.getState())
//                    .build();
//            return conversion;
//        }
//        return null;
       Address myAddress = address.isPresent()? address.get() : null;
       return Optional.ofNullable(myAddress)
               .filter(theAddress -> myAddress != null)
               .map(theAddress -> {
                   return AddressDto.builder()
                           .houseNumber(theAddress.getHouseNumber())
                           .street(theAddress.getStreet())
                           .state(theAddress.getState())
                           .build();
               })
               .orElse(null);
    }

    public PersonalDetail buildPersonalDetail(PersonalDetailDto personalDetailDto) {
            PersonalDetail conversion = PersonalDetail.builder()
                    .weight(personalDetailDto.weight())
                    .bloodGroup(getString(Optional.ofNullable(personalDetailDto.bloodGroup())))
                    .genoType(getString(Optional.ofNullable(personalDetailDto.genoType())))
                    .build();
            return conversion;
    }

    public List<PatientNextOfKin> returnNextOfKins(List<PatientNextOfKinDto> nextOfKinDtos) {
        return nextOfKinDtos.stream()
                .map(nextOfKinDto -> buildPatientNextOfKin(nextOfKinDto))
                .collect(Collectors.toList());
    }

    public PatientNextOfKin buildPatientNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
        return PatientNextOfKin.builder()
                .person(
                        buildPerson(
                                patientNextOfKinDto.first_name(),
                                Optional.ofNullable(patientNextOfKinDto.last_name()),
                                patientNextOfKinDto.gender()
                        )
                )
                .address(
                        addressBuilder(
                                Optional.ofNullable(patientNextOfKinDto.address()
                                ))
                )
                .build();
    }

    public PersonDto buildPersonDto(PatientDto patientDto) {
       return PersonDto.builder()
                .firstName(patientDto.first_name())
                .lastName(getString(Optional.ofNullable(patientDto.last_name())))
                //.gender(getGenderEnum(patientDto.gender()))
                .build();
    }

    public PatientNextOfKin convertToNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
        Address theAddress = Optional.ofNullable(patientNextOfKinDto.address())
                .map(pNXKAddress -> addressBuilder(Optional.ofNullable(patientNextOfKinDto.address())))
                .orElse(null);
        PersonDto personDto = PersonDto.builder()
                .firstName(patientNextOfKinDto.first_name())
                .lastName(getString(Optional.ofNullable(patientNextOfKinDto.last_name())))
                //.gender(getGenderEnum(patientNextOfKinDto.gender()))
                .build();
        PatientNextOfKin theKin = PatientNextOfKin.builder()
                .address(theAddress)
                .person(Person.builder()
                        .firstName(personDto.firstName())
                        .lastName(personDto.lastName())
                       // .gender(personDto.gender())
                        .build())
                .build();
        return theKin;
    }

    public Person buildPerson (String firstName, Optional<String> lastName, String gender) {
        String theLastName;
        if (lastName.isPresent()) {
            theLastName = lastName.get();
        } else {
            theLastName = null;
        }
        Gender theGender = getGenderEnum(gender);
        return Person.builder()
                .firstName(firstName)
                .lastName(theLastName)
                .gender(theGender)
                .build();
    }

    public PersonalDetailDto buildPersonalDetailDto(Optional<PersonalDetail> personalDetail) {
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

    public List<PatientNextOfKinDto> returnNextOfKinsDto(Optional<List<PatientNextOfKin>> nextOfKins) {
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

    public PatientNextOfKinDto buildPatientNextOfKinDto(PatientNextOfKin patientNextOfKin) {
        return PatientNextOfKinDto.builder()
                .first_name(patientNextOfKin.getPerson().getFirstName())
                .last_name(getString(Optional.ofNullable(patientNextOfKin.getPerson().getLastName())))
                .gender(getGenderString(patientNextOfKin.getPerson().getGender()))
                .address(buildAddressDto(Optional.ofNullable(patientNextOfKin.getAddress())))
                .build();
    }

    public List<PatientNextOfKinResponse> returnNextOfKinsResponse(Optional<List<PatientNextOfKin>> nextOfKins) {
        List<PatientNextOfKinResponse> theNextOfKinResponse = new ArrayList<>();
        if (nextOfKins.isPresent()) {
            if (nextOfKins.get().size() > 0) {
                theNextOfKinResponse = nextOfKins.get().stream()
                        .map(nextOfKin -> buildPatientNextOfKinResponse(nextOfKin))
                        .collect(Collectors.toList());

            }
        }
        return theNextOfKinResponse;
    }

    public PatientNextOfKinResponse buildPatientNextOfKinResponse (PatientNextOfKin patientNextOfKin) {
        return PatientNextOfKinResponse.builder()
                .id(patientNextOfKin.getId())
                .first_name(patientNextOfKin.getPerson().getFirstName())
                .last_name(getString(Optional.ofNullable(patientNextOfKin.getPerson().getLastName())))
                .gender(getGenderString(patientNextOfKin.getPerson().getGender()))
                .address(buildAddressDto(Optional.ofNullable(patientNextOfKin.getAddress())))
                .build();
    }

    private Address addressBuilder(Optional<AddressDto> myAddressDto) {
        return myAddressDto
                .map(addressDto -> buildAddress(addressDto))
                .orElse(null);
    }
    private PersonalDetail personalDetailBuilder(Optional<PersonalDetailDto> myPersonalDetailDto) {
        return myPersonalDetailDto
                .map(personalDetailDto -> buildPersonalDetail(personalDetailDto))
                .orElse(null);
    }

    private List<PatientNextOfKin> buildPatientNextOfKinList(Optional<List<PatientNextOfKinDto>> myNextOfKinDtoList) {
        return myNextOfKinDtoList
                .map(patientNextOfKinDtos -> returnNextOfKins(patientNextOfKinDtos))
                .orElse(Collections.emptyList());
    }
}
