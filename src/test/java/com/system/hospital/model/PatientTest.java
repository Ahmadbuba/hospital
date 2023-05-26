package com.system.hospital.model;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.dto.PersonalDetailDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class PatientTest {
    @Test
    public void Test1() {
        var patient = Patient.builder()
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
        Assertions.assertThat(patient.getPatientNextOfKinList()).isNull();
        Assertions.assertThat(patient.getRecords()).isNull();
        Assertions.assertThat(patient.getPersonalDetail()).isNotNull();
    }

    @Test
    public void Test2() {
        var patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Fatima")
                        .lastName("Buba")
                        .gender(Gender.FEMALE)
                        .build())
                .build();
        Assertions.assertThat(patient.getAddress()).isNull();
        Assertions.assertThat(patient.getPerson().getGender()).isEqualTo(Gender.FEMALE);
        Assertions.assertThat(patient.getId()).isEqualTo(0L);
    }

    @Test
    public void Test3() throws Exception {
        var patient = Patient.builder()
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
        var patientDto = PatientDto.builder()
                .first_name("Amina")
                .gender("Female")
                .address(AddressDto.builder()
                        .houseNumber(2)
                        .street("Zikwe")
                        .build())
                .build();
        Method method = Patient.class.getDeclaredMethod("updateTheAddress", Optional.class);
         method.setAccessible(true);
         method.invoke(patient, Optional.of(patientDto.address()));
         Assertions.assertThat(patient.getAddress().getHouseNumber()).isNotEqualTo(12);
         Assertions.assertThat(patient.getAddress().getHouseNumber()).isEqualTo(2);
         Assertions.assertThat(patient.getAddress().getState()).isEqualTo("Abuja");

    }

    @Test
    public void Test4() throws Exception {
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
        var patientDto = PatientDto.builder()
                .first_name("Amina")
                .gender("Female")
                .address(AddressDto.builder()
                        .houseNumber(24)
                        .street("Zikwe")
                        .build())
                .build();
        Method method = Patient.class.getDeclaredMethod("updateTheAddress", Optional.class);
        method.setAccessible(true);
        method.invoke(patient, Optional.of(patientDto.address()));
        Assertions.assertThat(patient.getAddress().getHouseNumber()).isEqualTo(24);
        Assertions.assertThat(patient.getAddress().getStreet()).isEqualTo("Zikwe");
        Assertions.assertThat(patient.getAddress().getState()).isNull();

    }

    @Test
    public void test5() throws Exception {
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
                .address(Address.builder()
                        .id(2)
                        .houseNumber(27)
                        .street("Khartoum")
                        .state("Abuja")
                        .build())
                .build();


        var nextOfKin1 = PatientNextOfKinDto.builder()
                .first_name("Iliya")
                .last_name("Fave")
                .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(23)
                        .street("Koula")
                        .state("Katsina")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKinDto.builder()
                        .first_name("David")
                        .last_name("Cho")
                        .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(66)
                        .street("dula")
                        .state("Katsina")
                        .build())
                .build();
        var nextOfKin3 = PatientNextOfKinDto.builder()
                        .first_name("Christabel")
                        .last_name("Katty")
                        .gender("Female")
                .address(AddressDto.builder()
                        .houseNumber(44)
                        .street("Koula")
                        .state("Zamfara")
                        .build())
                .build();
        var nextOfKinList = List.of(
                nextOfKin1,
                nextOfKin2,
                nextOfKin3
        );

        Method method = Patient.class.getDeclaredMethod("updateTheKins", Optional.class);
        method.setAccessible(true);
        method.invoke(patient, Optional.of(nextOfKinList));
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(3);
    }

    @Test
    public void test6() throws Exception {
        var nextOfKin = PatientNextOfKin.builder()
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
                .patientNextOfKinList(List.of(nextOfKin))
                .build();

        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(1);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getAddress().getHouseNumber()).isEqualTo(12);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getAddress().getStreet()).isEqualTo("Maitama");

        var nextOfKin1 = PatientNextOfKinDto.builder()
                .first_name("Iliya")
                .last_name("Fave")
                .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(23)
                        .street("Koula")
                        .state("Katsina")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKinDto.builder()
                .first_name("David")
                .last_name("Cho")
                .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(66)
                        .street("dula")
                        .state("Katsina")
                        .build())
                .build();
        var nextOfKin3 = PatientNextOfKinDto.builder()
                .first_name("Christabel")
                .last_name("Katty")
                .gender("Female")
                .address(AddressDto.builder()
                        .houseNumber(44)
                        .street("Koula")
                        .state("Zamfara")
                        .build())
                .build();
        var nextOfKinList = List.of(
                nextOfKin1,
                nextOfKin2,
                nextOfKin3
        );

        Method method = Patient.class.getDeclaredMethod("updateTheKins", Optional.class);
        method.setAccessible(true);
        method.invoke(patient, Optional.of(nextOfKinList));
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(3);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getAddress().getHouseNumber()).isEqualTo(23);
    }

    @Test
    public void updatePatientTest1() {
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
                .address(Address.builder()
                        .id(2)
                        .houseNumber(27)
                        .street("Khartoum")
                        .state("Abuja")
                        .build())
                .build();
        var patientDto = PatientDto.builder()
                .first_name("Amina")
                .gender("Female")
                .address(AddressDto.builder()
                        .houseNumber(2)
                        .street("Zikwe")
                        .build())
                .build();
        patient.updatePatient(patientDto);
        Assertions.assertThat(patient.getPerson().getFirstName()).isEqualTo("Amina");
        Assertions.assertThat(patient.getPerson().getLastName()).isEqualTo("Buba");
        Assertions.assertThat(patient.getAddress().getStreet()).isEqualTo("Zikwe");
        Assertions.assertThat(patient.getAddress().getState()).isEqualTo("Abuja");
        Assertions.assertThat(patient.getPersonalDetail().getGenoType()).isEqualTo("AS");
    }

    @Test
    public void updatePatientTest2() {
        var patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .lastName("Yusuf")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Assertions.assertThat(patient.getAddress()).isNull();
        Assertions.assertThat(patient.getPersonalDetail()).isNull();
        Assertions.assertThat(patient.getPatientNextOfKinList()).isNull();
        var nextOfKin1 = PatientNextOfKinDto.builder()
                .first_name("Iliya")
                .last_name("Fave")
                .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(23)
                        .street("Koula")
                        .state("Katsina")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKinDto.builder()
                .first_name("David")
                .last_name("Cho")
                .gender("Male")
                .address(AddressDto.builder()
                        .houseNumber(66)
                        .street("dula")
                        .state("Katsina")
                        .build())
                .build();
        var nextOfKin3 = PatientNextOfKinDto.builder()
                .first_name("Christabel")
                .last_name("Katty")
                .gender("Female")
                .address(AddressDto.builder()
                        .houseNumber(44)
                        .street("Koula")
                        .state("Zamfara")
                        .build())
                .build();
        var nextOfKinList = List.of(
                nextOfKin1,
                nextOfKin2,
                nextOfKin3
        );
        var patientDto = PatientDto.builder()
                .first_name("Ahmad")
                .gender("Male")
                .personal_details(PersonalDetailDto.builder()
                        .weight(67)
                        .bloodGroup("0")
                        .genoType("AA")
                        .build())
                .address(AddressDto.builder()
                        .houseNumber(2)
                        .street("Zikwe")
                        .build())
                .next_of_kins(nextOfKinList)
                .build();
        patient.updatePatient(patientDto);
        Assertions.assertThat(patient.getPerson().getFirstName()).isEqualTo("Ahmad");
        Assertions.assertThat(patient.getPerson().getLastName()).isEqualTo("Yusuf");
        Assertions.assertThat(patient.getPersonalDetail().getGenoType()).isEqualTo("AA");
        Assertions.assertThat(patient.getAddress().getStreet()).isEqualTo("Zikwe");
        Assertions.assertThat(patient.getAddress().getState()).isNull();
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(3);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(1).getPerson().getFirstName()).isEqualTo("David");
    }
}
