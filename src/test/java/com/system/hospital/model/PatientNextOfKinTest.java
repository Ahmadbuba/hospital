package com.system.hospital.model;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PatientNextOfKinTest {

    @Test
    public void Test1() {
        var patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Fatima")
                        .lastName("Buba")
                        .gender(Gender.FEMALE)
                        .build())
                .personalDetail(PersonalDetail.builder()
                        .genoType("AS")
                        .weight(55)
                        .bloodGroup("O")
                        .build())
                .build();
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .id(2)
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        Assertions.assertThat(nextOfKin1.getPatient()).isNull();
        nextOfKin1.updateNextOfKinPatient(patient);
        Assertions.assertThat(nextOfKin1.getPerson().getFirstName()).isEqualTo("Wealth");
        Assertions.assertThat(nextOfKin1.getPatient()).isNotNull();
        Assertions.assertThat(nextOfKin1.getId()).isEqualTo(0L);
        Assertions.assertThat(nextOfKin1.getAddress().getHouseNumber()).isEqualTo(12);
    }
}
