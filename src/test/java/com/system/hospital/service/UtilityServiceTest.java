package com.system.hospital.service;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.dto.PersonalDetailDto;
import com.system.hospital.model.*;
import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UtilityServiceTest {

    @Test
    public void getGenderEnumReturnsMaleWhenAllMaleAllInSmallCaps() {
        Gender gender = UtilityService.getGenderEnum("male");
        Assertions.assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderEnumReturnsMaleWhenCapsMaleIsGiven() {
        Gender gender = UtilityService.getGenderEnum("MALE");
        Assertions.assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderEnumReturnsMaleWhenFirstLetterOfMaleGivenIsInCap() {
        Gender gender = UtilityService.getGenderEnum("Male");
        Assertions.assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderEnumReturnsFemaleEnumWhenStringAnyStringApartFromMale() {
        Gender gender = UtilityService.getGenderEnum("F");
        Assertions.assertThat(gender).isEqualTo(Gender.FEMALE);
    }

    @Test
    public void getGenderEnumDoesNotReturnMaleEnumWhenAnyStringApartFromMaleIsProivided() {
        Gender gender = UtilityService.getGenderEnum("F");
        Assertions.assertThat(gender).isNotEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderStringReturnValueIsInCAPS() {
        String genderStringValue = UtilityService.getGenderString(Gender.MALE);
        Assertions.assertThat(genderStringValue).isEqualTo("MALE");
    }

    @Test
    public void getGenderStringReturnValueIsCAPSSensitive() {
        String genderStringValue = UtilityService.getGenderString(Gender.MALE);
        Assertions.assertThat(genderStringValue).isNotEqualTo("Male");
    }

    @Test
    public void getGenderStringReturnValueIsFemaleinCAPS() {
        String genderStringValue = UtilityService.getGenderString(Gender.MALE);
        Assertions.assertThat(genderStringValue).isNotEqualTo("FEMALE");
    }

    @Test
    public void getStringIsNull() {
        String result = UtilityService.getString(Optional.ofNullable(null));
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void getStringIsNotNull() {
        String result = UtilityService.getString(Optional.ofNullable("Ahmad"));
        Assertions.assertThat(result).isEqualTo("Ahmad");
    }

    @Test
    public void buildPersonReturnsInstanceOfPersonClass() {
       Assertions.assertThat(UtilityService.buildPerson("Ahmad","Buba",Gender.MALE)).isInstanceOf(Person.class);
    }

    @Test
    public void buildPersonProperties() {
        Person person = UtilityService.buildPerson("Ahmad","Buba",Gender.MALE);
        Assertions.assertThat(person.getFirstName()).isEqualTo("Ahmad");
        Assertions.assertThat(person.getLastName()).isEqualTo("Buba");
        Assertions.assertThat(person.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    public void buildPersonPropertiesNotSame() {
        Person person = UtilityService.buildPerson("Ahmad","Buba",Gender.MALE);
        Assertions.assertThat(person.getFirstName()).isNotEqualTo("Fatima");
        Assertions.assertThat(person.getLastName()).isNotEqualTo("Mohammed");
        Assertions.assertThat(person.getGender()).isNotEqualTo(Gender.FEMALE);
    }
    @Test
    public void buildPersonReturnNotNull() {
        Person person = UtilityService.buildPerson("Ahmad","Buba",Gender.MALE);
        Assertions.assertThat(person).isNotNull();
    }

    @Test
    public void buildAddressIsNotNull() {
        AddressDto addressDto = AddressDto.builder()
                .houseNumber(30)
                .street("Douala")
                .state("Abuja")
                .build();
        Assertions.assertThat(UtilityService.buildAddress(Optional.ofNullable(addressDto))).isNotNull();
    }
    @Test
    public void buildAddressIsNull() {
        Assertions.assertThat(UtilityService.buildAddress(Optional.ofNullable(null))).isNull();
    }

    @Test
    public void buildAddressPropertiesValues() {
        AddressDto addressDto = AddressDto.builder()
                .houseNumber(30)
                .street("Douala")
                .state("Abuja")
                .build();
        Address address = UtilityService.buildAddress(Optional.ofNullable(addressDto));
        Assertions.assertThat(address.getHouseNumber()).isEqualTo(addressDto.houseNumber());
        Assertions.assertThat(address.getStreet()).isEqualTo(addressDto.street());
        Assertions.assertThat(address.getState()).isEqualTo(addressDto.state());
    }

    @Test
    public void buildPersonalDetailValues() {
        PersonalDetailDto personalDetailDto = PersonalDetailDto.builder()
                .weight(55)
                .genoType("AS")
                .bloodGroup("0")
                .build();
        PersonalDetail personalDetail = UtilityService.buildPersonalDetail(Optional.ofNullable(personalDetailDto));
        Assertions.assertThat(personalDetail.getWeight()).isEqualTo(personalDetailDto.weight());
        Assertions.assertThat(personalDetail.getGenoType()).isEqualTo(personalDetailDto.genoType());
        Assertions.assertThat(personalDetail.getBloodGroup()).isEqualTo(personalDetailDto.bloodGroup());
    }

    @Test
    public void buildPersonalDetailisNull() {
        Assertions.assertThat(UtilityService.buildPersonalDetail(Optional.ofNullable(null))).isNull();
    }

    @Test
    public void buildPersonalDetailIsNotNull() {
        PersonalDetailDto personalDetailDto = PersonalDetailDto.builder()
                .weight(55)
                .genoType("AS")
                .bloodGroup("0")
                .build();
        Assertions.assertThat(UtilityService.buildPersonalDetail(Optional.ofNullable(personalDetailDto))).isNotNull();

    }

    @Test
    public void buildPatientNextOfKinValues() {
        AddressDto addressDto = AddressDto.builder()
                .houseNumber(30)
                .street("Douala")
                .state("Abuja")
                .build();
       PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .first_name("James")
                .last_name("Gana")
                .gender("Male")
                .address(addressDto)
                .build();
       PatientNextOfKin patientNextOfKin = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName(patientNextOfKinDto.first_name())
                        .lastName(patientNextOfKinDto.last_name())
                        .gender(UtilityService.getGenderEnum(patientNextOfKinDto.gender()))
                        .build())
                .address(Address.builder()
                        .houseNumber(patientNextOfKinDto.address().houseNumber())
                        .street(patientNextOfKinDto.address().street())
                        .state(patientNextOfKinDto.address().state())
                        .build())
                .build();
        Assertions.assertThat(UtilityService.buildPatientNextOfKin(patientNextOfKinDto).getPerson().getFirstName()).isEqualTo(patientNextOfKin.getPerson().getFirstName());
        Assertions.assertThat(UtilityService.buildPatientNextOfKin(patientNextOfKinDto).getAddress()).isNotNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKin(patientNextOfKinDto).getAddress().getState()).isEqualTo(patientNextOfKinDto.address().state());
    }

    @Test
    public void buildPatientNextOfKinValueReturnsNullForAddressIfAddressNotProvided() {
        PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .first_name("James")
                .last_name("Gana")
                .gender("Male")
                .build();
        Assertions.assertThat(UtilityService.buildPatientNextOfKin(patientNextOfKinDto).getAddress()).isNull();
    }

    @Test
    public void returnNextOfKins() {
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(null)).size()).isNotNull();
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(null)).size()).isEqualTo(0);
    }

    @Test
    public void returnNextOfKins2() {
        List<PatientNextOfKinDto> theNextOfKinDtos = new ArrayList<>();
        PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .first_name("James")
                .last_name("Gana")
                .gender("Male")
                .build();
        theNextOfKinDtos.add(patientNextOfKinDto);
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(theNextOfKinDtos)).size()).isEqualTo(1);
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(theNextOfKinDtos)).get(0).getAddress()).isNull();
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(theNextOfKinDtos)).get(0).getPerson().getFirstName()).isEqualTo(patientNextOfKinDto.first_name());
    }

    @Test
    public void convertPatientDtoToPatient() {
        AddressDto addressDto = AddressDto.builder()
                .houseNumber(30)
                .street("Douala")
                .state("Abuja")
                .build();
        PatientDto patientDto = PatientDto.builder()
                .first_name("Alfaki")
                .last_name("Isa")
                .gender("Male")
                .address(addressDto)
                .build();
        Assertions.assertThat(UtilityService.convertPatientDtoToPatient(patientDto).getPersonalDetail()).isNull();
        Assertions.assertThat(UtilityService.convertPatientDtoToPatient(patientDto).getPatientNextOfKinList().size()).isEqualTo(0);
        Assertions.assertThat(UtilityService.convertPatientDtoToPatient(patientDto).getRecords()).isNull();
    }

    @Test
    public void convertPatientDtoToPatient2() {
        List<PatientNextOfKinDto> theNextOfKinDtos = new ArrayList<>();
        PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .first_name("James")
                .last_name("Gana")
                .gender("Male")
                .build();
        theNextOfKinDtos.add(patientNextOfKinDto);
        AddressDto addressDto = AddressDto.builder()
                .houseNumber(30)
                .street("Douala")
                .state("Abuja")
                .build();
        PatientDto patientDto = PatientDto.builder()
                .first_name("Alfaki")
                .last_name("Isa")
                .gender("Male")
                .address(addressDto)
                .next_of_kins(theNextOfKinDtos)
                .build();

        Assertions.assertThat(UtilityService.convertPatientDtoToPatient(patientDto).getPatientNextOfKinList().size()).isEqualTo(1);
    }

    @Test
    public void convertFromPatientToPatientResponse() {
        Patient patient = Patient.builder()
                .id(1)
                .person(Person.builder()
                        .firstName("Fatima")
                        .lastName("Buba")
                        .gender(Gender.FEMALE)
                        .build())
                .address(Address.builder()
                        .id(2)
                        .houseNumber(12)
                        .street("Khartoum")
                        .state("Abuja")
                        .build())
                .personalDetail(PersonalDetail.builder()
                        .genoType("AS")
                        .weight(55)
                        .bloodGroup("O")
                        .build())
                .build();
        Assertions.assertThat(UtilityService.convertFromPatientToPatientResponse(patient).address()).isNotNull();
        Assertions.assertThat(UtilityService.convertFromPatientToPatientResponse(patient).next_of_kins().size()).isEqualTo(0);
    }
}
