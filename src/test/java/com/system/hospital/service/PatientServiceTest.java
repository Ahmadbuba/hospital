package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.model.*;
import com.system.hospital.service.impl.PatientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientServiceTest {
    @Autowired
   private PatientServiceImpl patientService;

    @Test
    public void test6() {
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


        var nextOfKinDto1 = PatientNextOfKinDto.builder()
                .first_name("Kana")
                .last_name("Kay")
                .gender("male")
                .build();

        var nextOfKinDto2 = PatientNextOfKinDto.builder()
                .first_name("Micheal")
                .last_name("Opara")
                .gender("male")
                .build();
        var nextOfKinDto3 = PatientNextOfKinDto.builder()
                .first_name("Mitchel")
                .last_name("Kun")
                .gender("male")
                .build();


        var patientDto = PatientDto.builder()
                .first_name("Fatima")
                .last_name("Buba")
                .gender("Female")
                .next_of_kins(List.of(nextOfKinDto1))
                .build();
        long patientId = patientService.createPatient(patientDto);
        var updatePatientDto = PatientDto.builder()
                .first_name("Fatima")
                .last_name("Buba")
                .gender("Female")
                .next_of_kins(List.of(nextOfKinDto2,nextOfKinDto3))
                .build();
        patientService.updatePatient(patientDto, patientId);

        Assertions.assertThat(patientService.getPatientById(patientId).next_of_kins().size()).isEqualTo(2);
//        Assertions.assertThat(patientNextOfKinRepository.findAll().size()).isEqualTo(0);

    }
}
