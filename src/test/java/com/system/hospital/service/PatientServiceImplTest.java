package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.model.Gender;
import com.system.hospital.model.Patient;
import com.system.hospital.model.PatientNextOfKin;
import com.system.hospital.model.Person;
import com.system.hospital.repository.PatientNextOfKinRepository;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.impl.PatientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PatientServiceImplTest {

    @Mock
    private UtilityService utilityService;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientNextOfKinRepository patientNextOfKinRepository;
    @Captor
    private ArgumentCaptor<Patient> patientCaptor;
    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.initMocks(this);
        this.patientService = new PatientServiceImpl(patientRepository,patientNextOfKinRepository,utilityService);
    }

    @Test
    public void canGetAllPatients() {

        //when
        patientService.getAllPatients(Optional.ofNullable(null));

        //then
        verify(patientRepository).findAll();
        verify(patientRepository, Mockito.never()).findByPersonFirstName("Ahmad");
    }

    @Test
    public void canGetAllPatients2() {

        //when
        patientService.getAllPatients(Optional.ofNullable("Ahmad"));

        //then
        verify(patientRepository, Mockito.never()).findAll();
        verify(patientRepository).findByPersonFirstName("Ahmad");
    }

    @Test
    public void canCreatePatient() {
        // Create a sample patientDto
        var patientNextOfKinDto1 = PatientNextOfKinDto.builder()
                .first_name("James")
                .gender("Male")
                .build();
        var patientNextOfKinDto2 = PatientNextOfKinDto.builder()
                .first_name("Fatima")
                .gender("Female")
                .build();

        List<PatientNextOfKinDto> patientNextOfKinDtoList = List.of(
                patientNextOfKinDto1,
                patientNextOfKinDto2
        );
        PatientDto patientDto = PatientDto.builder()
                .first_name("Ahmad")
                .gender("Male")
                .next_of_kins(patientNextOfKinDtoList)
                .build();
// Set necessary properties in the patientDto

        var patientNextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("James")
                        .gender(Gender.MALE)
                        .build())
                .build();
        var patientNextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Fatima")
                        .gender(Gender.FEMALE)
                        .build())
                .build();

        List<PatientNextOfKin> patientNextOfKinList = List.of(
                patientNextOfKin1,
                patientNextOfKin2
        );

// Mock the UtilityService.convertPatientDtoToPatient(patientDto) method
        Patient patient = Patient.builder()
                .id(3)
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .patientNextOfKinList(patientNextOfKinList)
                .build();
        when(utilityService.convertPatientDtoToPatient(patientDto)).thenReturn(patient);

// Mock the patientRepository.save(patient) method
        when(patientRepository.save(patient)).thenReturn(patient);


// Mock the patientNextOfKinRepository.save(patientNextOfKin) method
        when(patientNextOfKinRepository.save(any(PatientNextOfKin.class))).thenAnswer(invocation -> invocation.getArgument(0));


// Call the createPatient method
        long patientId = patientService.createPatient(patientDto);

// Verify that patientRepository.save(patient) is invoked once
        verify(patientRepository, times(1)).save(any(Patient.class));

// Verify that patientNextOfKin.updateNextOfKinPatient(patient) is invoked once
        verify(patientNextOfKin1, times(1)).updateNextOfKinPatient(patient);
        verify(patientNextOfKin2, times(1)).updateNextOfKinPatient(patient);


// Verify that patientNextOfKinRepository.save(patientNextOfKin) is invoked once
        verify(patientNextOfKinRepository, times(2)).save(any(PatientNextOfKin.class));


// Assert the returned patientId
// Add any additional assertions as needed
    }

    public void test6() {
        when(patientNextOfKinRepository.save(any(PatientNextOfKin.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }
}
