package com.system.hospital.repository;

import com.system.hospital.HospitalApplication;
import com.system.hospital.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {
    @Autowired
    private PatientRepository patientRepository;

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
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

}