package com.system.hospital.service;

import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.dto.PatientResponse;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.Gender;
import com.system.hospital.model.Patient;
import com.system.hospital.model.PatientNextOfKin;
import com.system.hospital.model.Person;
import com.system.hospital.repository.PatientNextOfKinRepository;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.impl.PatientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;


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
    public void createPatient() {
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

        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .patientNextOfKinList(patientNextOfKinList)
                .build();
        lenient().when(utilityService.convertPatientDtoToPatient(patientDto)).thenReturn(patient);

        lenient().when(patientRepository.save(patient)).thenReturn(patient);

        long patientId = patientService.createPatient(patientDto);

        verify(patientRepository, times(1)).save(any(Patient.class));

        Assertions.assertThat(patientId).isEqualTo(patient.getId());

    }
    @Test
    public void getAllPatients_whenOptionalIs_Null() {
        Patient patient1 = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient2 = Patient.builder()
                .person(Person.builder()
                        .firstName("Alfaki")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient3 = Patient.builder()
                .person(Person.builder()
                        .firstName("Fatima")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient4 = Patient.builder()
                .person(Person.builder()
                        .firstName("Hauwa")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient5 = Patient.builder()
                .person(Person.builder()
                        .firstName("Usman")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient6 = Patient.builder()
                .person(Person.builder()
                        .firstName("Aisha")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient7 = Patient.builder()
                .person(Person.builder()
                        .firstName("Maryam")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        List<Patient> allPatients = List.of(
                patient1,
                patient2,
                patient3,
                patient4,
                patient5,
                patient6,
                patient7
        );

        PatientResponse patientResponse =  PatientResponse.builder()
                .firstName("Ahmad")
                .lastName("Buba")
                .gender("Male")
                .build();


        when(patientRepository.findAll()).thenReturn(allPatients);
        when(utilityService.convertFromPatientToPatientResponse(any(Patient.class))).thenReturn(patientResponse);

        List<PatientResponse> returnedPatients = patientService.getAllPatients(Optional.ofNullable(null));

        verify(patientRepository,Mockito.never()).findByPersonFirstName(anyString());
        verify(patientRepository,times(1)).findAll();
        verify(utilityService,times(7)).convertFromPatientToPatientResponse(any(Patient.class));
        Assertions.assertThat(returnedPatients.size()).isEqualTo(7);
    }

    @Test
    public void getAllPatients_whenOptionalIsNOT_Null() {
        Patient patient1 = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient2 = Patient.builder()
                .person(Person.builder()
                        .firstName("Alfaki")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();
        Patient patient3 = Patient.builder()
                .person(Person.builder()
                        .firstName("Fatima")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();

        PatientResponse patientResponse =  PatientResponse.builder()
                .firstName("Ahmad")
                .lastName("Buba")
                .gender("Male")
                .build();
        List<Patient> firstNameOfAhmad = List.of(patient1);


        when(patientRepository.findByPersonFirstName("Ahmad")).thenReturn(firstNameOfAhmad);
        when(utilityService.convertFromPatientToPatientResponse(any(Patient.class))).thenReturn(patientResponse);

        List<PatientResponse> returnedPatients = patientService.getAllPatients(Optional.of("Ahmad"));

        verify(patientRepository,Mockito.never()).findAll();
        verify(patientRepository,times(1)).findByPersonFirstName(anyString());
        verify(utilityService,times(1)).convertFromPatientToPatientResponse(any(Patient.class));

        Assertions.assertThat(returnedPatients.size()).isEqualTo(1);
    }

    @Test
    public void getPatientById_whenPatientId_DoesExsist() {
        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .lastName("Buba")
                        .gender(Gender.MALE)
                        .build())
                .build();

        PatientResponse patientResponse =  PatientResponse.builder()
                .firstName("Ahmad")
                .lastName("Buba")
                .gender("Male")
                .build();
        long val = 4;
        when(patientRepository.findById(val)).thenReturn(Optional.of(patient));
        when(utilityService.convertFromPatientToPatientResponse(any(Patient.class))).thenReturn(patientResponse);


        PatientResponse returnedPatient = patientService.getPatientById(val);


        verify(patientRepository,times(1)).findById(val);
        verify(utilityService,times(1)).convertFromPatientToPatientResponse(any(Patient.class));
        Assertions.assertThat(returnedPatient.lastName()).isEqualTo("Buba");
    }
    @Test
    public void getPatientById_ThrowsRESOURCENOTFOUNDEXCEPTION_whenPatientId_DoesNotExsist() {
        PatientResponse patientResponse =  PatientResponse.builder()
                .firstName("Ahmad")
                .lastName("Buba")
                .gender("Male")
                .build();

        when(patientRepository.findById(eq(1L))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> patientService.getPatientById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Not found Patient with id =1");

        verify(patientRepository, times(1)).findById(1L);
        verifyNoInteractions(utilityService);
    }

    @Test
    public void updatePatient_Test_When_PatientDto_Has_NextOfKins() {
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


        var patientNextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Grace")
                        .gender(Gender.MALE)
                        .build())
                .build();
        var patientNextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Ummi")
                        .gender(Gender.FEMALE)
                        .build())
                .build();

        List<PatientNextOfKin> patientNextOfKinList = List.of(
                patientNextOfKin1,
                patientNextOfKin2
        );


        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .patientNextOfKinList(patientNextOfKinList)
                .build();
        doNothing().when(patientNextOfKinRepository).deleteAll(anyList());

        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        long updatedPatient = patientService.updatePatient(patientDto,1L);

        verify(patientRepository, times(1)).save(any(Patient.class));
        verify(patientNextOfKinRepository, times(1)).deleteAll(anyList());
    }

    @Test
    public void updatePatient_Test_When_PatientDto_Has_NO_NextOfKins() {
        // Create a sample patientDto
        PatientDto patientDto = PatientDto.builder()
                .first_name("Ahmad")
                .gender("Male")
                .build();


        var patientNextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Grace")
                        .gender(Gender.MALE)
                        .build())
                .build();
        var patientNextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Ummi")
                        .gender(Gender.FEMALE)
                        .build())
                .build();

        List<PatientNextOfKin> patientNextOfKinList = List.of(
                patientNextOfKin1,
                patientNextOfKin2
        );


        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .patientNextOfKinList(patientNextOfKinList)
                .build();

        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        long updatedPatient = patientService.updatePatient(patientDto,1L);

        verify(patientRepository, times(1)).save(any(Patient.class));
        verifyNoInteractions(patientNextOfKinRepository);
    }

    @Test
    public void updatePatient_Test_When_Patient_Has_NextOfKins_But_PatientDTO_Has_None() {
        // Create a sample patientDto

        PatientDto patientDto = PatientDto.builder()
                .first_name("Ahmad")
                .gender("Male")
                .build();


        var patientNextOfKin1 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Grace")
                        .gender(Gender.MALE)
                        .build())
                .build();
        var patientNextOfKin2 = PatientNextOfKin.builder()
                .person(Person.builder()
                        .firstName("Ummi")
                        .gender(Gender.FEMALE)
                        .build())
                .build();

        List<PatientNextOfKin> patientNextOfKinList = List.of(
                patientNextOfKin1,
                patientNextOfKin2
        );


        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .patientNextOfKinList(patientNextOfKinList)
                .build();


        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        long updatedPatient = patientService.updatePatient(patientDto,1L);

        verify(patientRepository, times(1)).save(any(Patient.class));
        verifyNoInteractions(patientNextOfKinRepository);
    }

    @Test
    public void updatePatient_Test_When_PatientDto_Has_NextOfKins_But_Patient_Has_No_NextOfKins() {
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



        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .build();

        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        long updatedPatient = patientService.updatePatient(patientDto,1L);

        verify(patientRepository, times(1)).save(any(Patient.class));
        verifyNoInteractions(patientNextOfKinRepository);
    }
    @Test
    public void deletePatient_When_PatientId_Exsists() {
        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .build();
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).delete(any(Patient.class));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).delete(any(Patient.class));
    }

    @Test
    public void deletePatient_When_PatientId_Does_Not_Exsist() {
        Patient patient = Patient.builder()
                .person(Person.builder()
                        .firstName("Ahmad")
                        .gender(Gender.MALE)
                        .build())
                .build();
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> patientService.deletePatient(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Not found Patient with id =1");

    }
}
