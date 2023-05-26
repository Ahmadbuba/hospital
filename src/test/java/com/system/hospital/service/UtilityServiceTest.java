package com.system.hospital.service;

import com.system.hospital.dto.*;
import com.system.hospital.model.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UtilityServiceTest {

    @Test
    public void getGenderEnumReturnsMaleEnumWhenInputIsMaleInAllSmallCaps() {
        Gender gender = UtilityService.getGenderEnum("male");
        Assertions.assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderEnumReturnsMaleEnumWhenInputIsMaleInAllCaps() {
        Gender gender = UtilityService.getGenderEnum("MALE");
        Assertions.assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderEnumReturnsMaleEnumWhenFirstLetterOfMaleInputGivenIsInCap() {
        Gender gender = UtilityService.getGenderEnum("Male");
        Assertions.assertThat(gender).isEqualTo(Gender.MALE);
    }

    @Test
    public void getGenderEnumReturnsFemaleEnumWhenInputIsAnyStringApartFromMale() {
        Gender gender = UtilityService.getGenderEnum("F");
        Assertions.assertThat(gender).isEqualTo(Gender.FEMALE);
    }

    @Test
    public void getGenderEnumDoesNotReturnMaleEnumWhenInputIsAnyStringThatIsNotMale() {
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
    public void getGenderStringReturnValueIsNotFemaleinCAPSOrSmall() {
        String genderStringValue = UtilityService.getGenderString(Gender.MALE);
        Assertions.assertThat(genderStringValue).isNotEqualTo("FEMALE");
        Assertions.assertThat(genderStringValue).isNotEqualTo("female");
        Assertions.assertThat(genderStringValue).isNotEqualTo("Female");
    }

    @Test
    public void getStringIsNullWhenValueGivenToOptionalInputOfStringIsNull() {
        String result = UtilityService.getString(Optional.ofNullable(null));
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void getStringIsNotNullWhenOptionalInputOfStringIsNotNull() {
        String result = UtilityService.getString(Optional.ofNullable("Ahmad"));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("Ahmad");
    }

    @Test
    public void buildPersonReturnsInstanceOfPersonClass() {
       Assertions.assertThat(UtilityService.buildPerson("Ahmad","Buba",Gender.MALE)).isInstanceOf(Person.class);
    }

    @Test
    public void buildPersonPropertiesValues() {
        Person person = UtilityService.buildPerson("Ahmad","Buba",Gender.MALE);
        Assertions.assertThat(person.getFirstName()).isEqualTo("Ahmad");
        Assertions.assertThat(person.getLastName()).isEqualTo("Buba");
        Assertions.assertThat(person.getGender()).isEqualTo(Gender.MALE);
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
    public void buildAddressReturnValueWhenOptionalOfAddressDtoisGivenAnAddressDtoIsNotNull() {
        AddressDto addressDto = AddressDto.builder()
                .houseNumber(30)
                .street("Douala")
                .state("Abuja")
                .build();
        Assertions.assertThat(UtilityService.buildAddress(Optional.of(addressDto))).isNotNull();
    }
    @Test
    public void buildAddressReturnValueWhenOptionalOfAddressDtoisGivenNullResultIsNull() {
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
        Assertions.assertThat(address.getState()).isNotEqualTo("Lagos");
    }

    @Test
    public void buildPersonalDetailValues() {
        PersonalDetailDto personalDetailDto = PersonalDetailDto.builder()
                .weight(55)
                .genoType("AS")
                .bloodGroup("0")
                .build();
        PersonalDetail personalDetail = UtilityService.buildPersonalDetail(Optional.of(personalDetailDto));
        Assertions.assertThat(personalDetail.getWeight()).isEqualTo(personalDetailDto.weight());
        Assertions.assertThat(personalDetail.getGenoType()).isEqualTo(personalDetailDto.genoType());
        Assertions.assertThat(personalDetail.getBloodGroup()).isEqualTo(personalDetailDto.bloodGroup());
        Assertions.assertThat(UtilityService.buildPersonalDetail(Optional.ofNullable(null))).isNull();
    }

    @Test
    public void buildPersonalDetailDtoReturnValues() {
        var personalDetail = PersonalDetail.builder()
                .genoType("AS")
                .weight(55)
                .bloodGroup("O")
                .build();
        Assertions.assertThat(UtilityService.buildPersonalDetailDto(Optional.of(personalDetail))).isNotNull();
        Assertions.assertThat(UtilityService.buildPersonalDetailDto(Optional.ofNullable(null))).isNull();
    }
    @Test
    public void buildPatientNextOfKinPropertiesValues() {
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
    public void returnNextOfKinsReturnsAnEmptyArrayListWhenOptionalOfNextOfKinDtoIsProvidedNull() {
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(null)).size()).isNotNull();
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.ofNullable(null)).size()).isEqualTo(0);
    }

    @Test
    public void returnNextOfKinsReturnsAnArrayListOfPatientNextOfKinWithTheSameSizeAsTheProvidedListOfPatientNextOfKinDto() {
        List<PatientNextOfKinDto> theNextOfKinDtos = new ArrayList<>();
        PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .first_name("James")
                .last_name("Gana")
                .gender("Male")
                .build();
        theNextOfKinDtos.add(patientNextOfKinDto);
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.of(theNextOfKinDtos)).size()).isEqualTo(1);
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.of(theNextOfKinDtos)).get(0).getAddress()).isNull();
        Assertions.assertThat(UtilityService.returnNextOfKins(Optional.of(theNextOfKinDtos)).get(0).getPerson().getFirstName()).isEqualTo(patientNextOfKinDto.first_name());
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
        Assertions.assertThat(UtilityService.convertPatientDtoToPatient(patientDto).getAddress()).isNotNull();
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

    @Test
    public void convertPatientNextOfKinDtoToNextOfKin() {
        PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .first_name("Wealth")
                .last_name("Smith")
                .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(55)
                        .street("Aminu Kano")
                        .state("Abuja")
                        .build())
                .build();

        Assertions.assertThat(UtilityService.convertToNextOfKin(patientNextOfKinDto).getAddress()).isNotNull();
        Assertions.assertThat(UtilityService.convertToNextOfKin(patientNextOfKinDto).getAddress().getStreet()).isEqualTo("Aminu Kano");
    }

    @Test
    public void convertPatientNextOfKinDtoToNextOfKin2() {
        PatientNextOfKinDto patientNextOfKinDto = PatientNextOfKinDto.builder()
                .last_name("Smith")
                .gender("Male")
                .build();

        Assertions.assertThat(UtilityService.convertToNextOfKin(patientNextOfKinDto).getAddress()).isNull();
        Assertions.assertThat(UtilityService.convertToNextOfKin(patientNextOfKinDto).getPerson().getFirstName()).isNull();

    }

    @Test
    public void buildPersonDTOTest() {
        var patientDto = PatientDto.builder()
                .first_name("James")
                .last_name("Gana")
                .gender("Male")
                .build();

        Assertions.assertThat(UtilityService.buildPersonDto(patientDto)).isNotNull();
        Assertions.assertThat(UtilityService.buildPersonDto(patientDto)).isInstanceOf(PersonDto.class);
    }

    @Test
    public void buildPatientNextOfKinDto() {
        var patientNextOfKin = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("kiara")
                        .lastName("Lee")
                        .gender(Gender.FEMALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(2)
                        .street("Khartoum")
                        .state("Kaduna")
                        .build())
                .build();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinDto(patientNextOfKin)).isNotNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinDto(patientNextOfKin).address()).isNotNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinDto(patientNextOfKin).first_name()).isEqualTo("kiara");
    }

    @Test
    public void buildPatientNextOfKinDto2() {
        var patientNextOfKin = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("kiara")
                        .gender(Gender.FEMALE)
                        .build())
                .build();

        Assertions.assertThat(UtilityService.buildPatientNextOfKinDto(patientNextOfKin).address()).isNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinDto(patientNextOfKin).last_name()).isNull();
    }

    @Test
    public void returnNextOfKinsDtoList() {
        var patientNextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Micheal")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(2)
                        .street("Khartoum")
                        .state("Kaduna")
                        .build())
                .build();
        var patientNextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("kiara")
                        .lastName("Lee")
                        .gender(Gender.FEMALE)
                        .build())
                .build();
        List <PatientNextOfKin> nextOfKins = List.of(
                patientNextOfKin1,
                patientNextOfKin2
                );

        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(nextOfKins)).size()).isEqualTo(2);
        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(nextOfKins)).get(0).address()).isNotNull();
        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(nextOfKins)).get(1).address()).isNull();
        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(nextOfKins)).get(1).last_name()).isNotNull();
        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(nextOfKins)).get(0).last_name()).isNull();
        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(nextOfKins)).get(1).last_name()).isEqualTo("Lee");
    }

    @Test
    public void returnNextOfKinsDtoList2() {
        Assertions.assertThat(UtilityService.returnNextOfKinsDto(Optional.ofNullable(null)).size()).isEqualTo(0);
    }

    @Test
    public void buildPatientNextOfKinResponse() {
        var patientNextOfKin = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("kiara")
                        .lastName("Lee")
                        .gender(Gender.FEMALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(2)
                        .street("Khartoum")
                        .state("Kaduna")
                        .build())
                .build();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinResponse(patientNextOfKin)).isNotNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinResponse(patientNextOfKin).address()).isNotNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinResponse(patientNextOfKin).first_name()).isEqualTo("kiara");
    }

    @Test
    public void buildPatientNextOfKinResponse2() {
        var patientNextOfKin = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Micheal")
                        .gender(Gender.MALE)
                        .build())

                .build();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinResponse(patientNextOfKin).address()).isNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinResponse(patientNextOfKin).last_name()).isNull();
        Assertions.assertThat(UtilityService.buildPatientNextOfKinResponse(patientNextOfKin).gender()).isEqualToIgnoringCase("Male");
    }

    @Test
    public void returnNextOfKinResponseList() {
        var patientNextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Micheal")
                        .gender(Gender.MALE)
                        .build())
                .build();
        var patientNextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("kiara")
                        .lastName("Lee")
                        .gender(Gender.FEMALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(2)
                        .street("Khartoum")
                        .state("Kaduna")
                        .build())
                .build();
        List <PatientNextOfKin> nextOfKins = List.of(
                patientNextOfKin1,
                patientNextOfKin2
        );
        Assertions.assertThat(UtilityService.returnNextOfKinsResponse(Optional.of(nextOfKins)).size()).isEqualTo(2);
        Assertions.assertThat(UtilityService.returnNextOfKinsResponse(Optional.of(nextOfKins)).get(0).address()).isNull();
        Assertions.assertThat(UtilityService.returnNextOfKinsResponse(Optional.of(nextOfKins)).get(1).address()).isNotNull();
    }


    @Test
    public void returnNextOfKinResponseList2() {
        Assertions.assertThat(UtilityService.returnNextOfKinsResponse(Optional.ofNullable(null)).size()).isEqualTo(0);
    }

}