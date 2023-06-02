package com.system.hospital.repository;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.model.*;
import com.system.hospital.service.impl.PatientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {
    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:14.4")
            .withUsername("sa")
            .withPassword("sa")
            .withDatabaseName("integration-tests-db");

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientNextOfKinRepository patientNextOfKinRepository;


    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
        patientNextOfKinRepository.deleteAll();
    }
    @Test
    public void getAllPatientsWherePersonFirstNameContaining() {

        // given
        Patient patient1 = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        patientRepository.save(patient1);

        Patient patient2 = Patient.builder()
                .person(Person.builder()
                        .firstName("Maryam")
                        .lastName("Buba")
                        .gender(Gender.FEMALE)
                        .build())
                .build();
        patientRepository.save(patient2);

        Patient patient3 = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .lastName("Yusuf")
                        .gender(Gender.MALE)
                        .build())
                .build();

        patientRepository.save(patient3);

        // when
        List<Patient> patients = patientRepository.findByPersonFirstName("Ahmad");

        // then
        Assertions.assertThat(patients.size()).isEqualTo(2);
        Assertions.assertThat(patients.get(0).getPerson().getFirstName()).isEqualTo("Ahmad");
        Assertions.assertThat(patients.get(1).getPerson().getFirstName()).isEqualTo("Ahmad");
    }

    @Test
    public void test2() {
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Caleb")
                        .lastName("Smith")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(59)
                        .street("Utako")
                        .state("Abuja")
                        .build())
                .build();
        List<PatientNextOfKin> nextOfKinList = List.of(nextOfKin1,nextOfKin2);
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
                .patientNextOfKinList(nextOfKinList)
                .build();
        patientRepository.save(patient);

        for (PatientNextOfKin patientNextOfKin: patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);

        }

        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(2);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPatient()).isEqualTo(patient);
        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void test3() {
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Caleb")
                        .lastName("Smith")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(59)
                        .street("Utako")
                        .state("Abuja")
                        .build())
                .build();
        List<PatientNextOfKin> nextOfKinList = List.of(nextOfKin1,nextOfKin2);
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
                .patientNextOfKinList(nextOfKinList)
                .build();
        patientRepository.save(patient);

        for (PatientNextOfKin patientNextOfKin: patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);

        }

        patientNextOfKinRepository.deleteAll(patient.getPatientNextOfKinList());
        patient.setPatientNextOfKinListNull();
        patientRepository.save(patient);
        Assertions.assertThat(patient.getPatientNextOfKinList()).isNull();
        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void onDeleteCascadeTestWhenPatientIsDeletedAllNextOfKinsAreDeletedAutomatically() {
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Caleb")
                        .lastName("Smith")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(59)
                        .street("Utako")
                        .state("Abuja")
                        .build())
                .build();
        List<PatientNextOfKin> nextOfKinList = List.of(nextOfKin1,nextOfKin2);
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
                .patientNextOfKinList(nextOfKinList)
                .build();
        patientRepository.save(patient);

        for (PatientNextOfKin patientNextOfKin: patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);

        }

        patientRepository.delete(patient);

        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void test7() {
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Caleb")
                        .lastName("Smith")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(59)
                        .street("Utako")
                        .state("Abuja")
                        .build())
                .build();
        List<PatientNextOfKin> nextOfKinList = List.of(nextOfKin1,nextOfKin2);
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
                .patientNextOfKinList(nextOfKinList)
                .build();
        patientRepository.save(patient);

        for (PatientNextOfKin patientNextOfKin: patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);

        }

        var nextOfKinDto1 = PatientNextOfKinDto.builder()
                .first_name("Kana")
                .last_name("Kay")
                .gender("male")
                .build();



        var patientDto = PatientDto.builder()
                .first_name("Fatima")
                .last_name("Buba")
                .gender("Female")
                .next_of_kins(List.of(nextOfKinDto1))
                .build();

        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(2);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPerson().getFirstName()).isEqualTo("Wealth");
        patientNextOfKinRepository.deleteAll(patient.getPatientNextOfKinList());
        patient.setPatientNextOfKinListNull();
        patientRepository.save(patient);
        Assertions.assertThat(patient.getPatientNextOfKinList()).isNull();
        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(0);
        patient.updatePatient(patientDto);
        patientRepository.save(patient);
        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(1);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPatient()).isEqualTo(patient);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPerson().getLastName()).isEqualTo("Kay");
    }

    @Test
    public void test8() {
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Caleb")
                        .lastName("Smith")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(59)
                        .street("Utako")
                        .state("Abuja")
                        .build())
                .build();
        List<PatientNextOfKin> nextOfKinList = List.of(nextOfKin1,nextOfKin2);
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
                .patientNextOfKinList(nextOfKinList)
                .build();
        patientRepository.save(patient);

        for (PatientNextOfKin patientNextOfKin: patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);

        }

        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(2);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPatient()).isEqualTo(patient);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPerson().getFirstName()).isEqualTo("Wealth");

    }

    @Test
    public void test9() {
        var nextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Wealth")
                        .lastName("Ngama")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(12)
                        .street("Maitama")
                        .state("Abuja")
                        .build())
                .build();
        var nextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Caleb")
                        .lastName("Smith")
                        .gender(Gender.MALE)
                        .build())
                .address(Address.builder()
                        .houseNumber(59)
                        .street("Utako")
                        .state("Abuja")
                        .build())
                .build();
        List<PatientNextOfKin> nextOfKinList = List.of(nextOfKin1,nextOfKin2);
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
                .patientNextOfKinList(nextOfKinList)
                .build();
        patientRepository.save(patient);

        for (PatientNextOfKin patientNextOfKin: patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);

        }

        var nextOfKinDto1 = PatientNextOfKinDto.builder()
                .first_name("Kana")
                .last_name("Kay")
                .gender("male")
                .build();



        var patientDto = PatientDto.builder()
                .first_name("Fatima")
                .last_name("Buba")
                .gender("Female")
                .next_of_kins(List.of(nextOfKinDto1))
                .build();

        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPerson().getLastName()).isEqualTo("Ngama");
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(2);
        patientNextOfKinRepository.deleteAll();
        patient.setPatientNextOfKinListNull();
        patient.updatePatient(patientDto);
        patientRepository.save(patient);
        Assertions.assertThat(patient.getPatientNextOfKinList().size()).isEqualTo(1);
        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPerson().getFirstName()).isEqualTo("Kana");
        Assertions.assertThat(patient.getPatientNextOfKinList().get(0).getPerson().getGender()).isEqualTo(Gender.MALE);
        Assertions.assertThat(patientNextOfKinRepository.findAll().get(0).getPerson().getFirstName()).isEqualTo("Kana");
        Assertions.assertThat(patientNextOfKinRepository.findAll().get(0).getPatient()).isEqualTo(patient);
    }


}