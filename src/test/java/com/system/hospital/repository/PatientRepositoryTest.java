package com.system.hospital.repository;

import com.system.hospital.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PatientRepositoryTest {
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void savePatient() {

        // given
        Patient patient =  Patient.builder()
                .firstName("Ahmad")
                .lastName("Buba")
                .gender(Gender.MALE).build();
        // when
        Patient savedPatient = patientRepository.save(patient);
        // then
        Assertions.assertThat(savedPatient).isNotNull();

    }

}