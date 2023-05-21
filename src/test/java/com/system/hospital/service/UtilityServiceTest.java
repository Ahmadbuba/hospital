package com.system.hospital.service;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.PersonalDetailDto;
import com.system.hospital.model.Address;
import com.system.hospital.model.Gender;
import com.system.hospital.model.Person;
import com.system.hospital.model.PersonalDetail;
import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
