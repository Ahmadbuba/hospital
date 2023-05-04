package com.system.hospital.service.impl;

import com.system.hospital.dto.*;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.*;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Override
    public void createPatient(PatientDto patientDto) {
        Person thePerson = buildPerson(patientDto);
        Patient thePatient = Patient.builder()
                .person(thePerson)
                .build();
        patientRepository.save(thePatient);


        Address address = Address.builder()
                .state(patientDto.address().state())
                .houseNumber(patientDto.address().houseNumber())
                .street(patientDto.address().street())
                .build();
        PersonalDetail  personalDetail = PersonalDetail.builder()
                .bloodGroup(patientDto.personal_details().bloodGroup())
                .weight(patientDto.personal_details().weight())
                .genoType(patientDto.personal_details().genoType())
                .build();
        PatientNextOfKin patientNextOfKin = PatientNextOfKin.builder()
                .patient(patientDto)
                .build();
        HealthRecord healthRecord;

    }


    @Override
    public List<PatientResponse> getAllPatients(Optional<String> firstName) {
        List<PatientResponse> allPatients = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        String theName = firstName.isPresent() ? firstName.get() : null;

        patients = Optional.ofNullable(theName)
                .filter(name -> !theName.isEmpty())
                .map(name -> patientRepository.findByPersonFirstName(name))
                .orElseGet(patientRepository::findAll);

        if (!patients.isEmpty()) {
            allPatients = patients.stream()
                    .map(patient -> convertToPatientResponseDto(patient))
                    .collect(Collectors.toList());
        }
        return allPatients;
    }

    @Override
    public PatientResponse getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));

        return convertToPatientResponseDto(patient);
    }

    @Override
    public PatientResponse updatePatient(PatientDto patientDto, long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        Patient updatedPatient = patientRepository.save(convertToPatientEntity(patientDto, id));
        return convertToPatientResponseDto(updatedPatient);
    }

    @Override
    public void deletePatient(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        patientRepository.delete(patient);
    }

    private Patient convertToPatientEntity(PatientDto patientDto, long id) {
        Patient thePatient = Patient.builder()
                .id(id)
                .person(Person.builder()
                        .firstName(patientDto.first_name())
                        .lastName(patientDto.last_name())
                        .gender(getGender(patientDto.gender()))
                        .build())
                .build();
        return thePatient;
    }



    private String convertEnumToString(Gender value) {
        String res = value.toString().toLowerCase();
        res = res.substring(0,1).toUpperCase() + res.substring(1);
        return res;
    }

    private static Gender getGender(String theGender) {
        return theGender.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE;
    }
    private PatientResponse convertToPatientResponseDto(Patient patient) {
        PatientResponse thePatient = PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getPerson().getFirstName())
                .lastName(patient.getPerson().getLastName())
                .gender(convertEnumToString(patient.getPerson().getGender()))
                .build();
        return thePatient;
    }

    private Address convertFromAddressDtoToAddress(Optional<AddressDto> addressToConvert) {
        Address theAddress = Optional.ofNullable(addressToConvert.get())
                .filter(address -> addressToConvert.isPresent())
                .map(address -> {
                    AddressDto add = addressToConvert.get();
                    Address  conversion = Address.builder()
                            .state(add.state())
                            .street(add.street())
                            .houseNumber(add.houseNumber())
                            .build();
                    return conversion;
                })
                .orElse(
                        null
                );
        return theAddress;
    }
    private PatientNextOfKin convertFromDtoToPatientNextOfKin(Optional<NextOfKinDto> nextOfKinDto) {
        PatientNextOfKin theKin = Optional.ofNullable(nextOfKinDto.get())
                .filter(thePerson -> nextOfKinDto.isPresent())
                .map(thePerson -> {
                    NextOfKinDto theNextofKin = nextOfKinDto.get();
                    PatientNextOfKin conversion = PatientNextOfKin.builder()
                            .person(Person.builder()
                                    .firstName(theNextofKin.first_name())
                                    .lastName(theNextofKin.last_name())
                                    .gender(getGender(theNextofKin.gender()))
                                    .build()
                            )
                            .address(convertFromAddressDtoToAddress(
                                    Optional.ofNullable(theNextofKin.address())
                                )
                            )
                            .build();
                    return conversion;
                })
                .orElse(
                        null
                );
        return theKin;
    }

    private Person buildPerson (String firstName, String lastName, String gender) {
        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender.equalsIgnoreCase("MALE")? Gender.MALE: Gender.FEMALE)
                .build();
    }

    private Patient convertFromPatientDtoToPatient(PatientDto patientDto) {
        Person thePerson = buildPerson(patientDto);
        Patient thePatient = Patient.builder()
                .person(thePerson)
                .address(
                        convertFromAddressDtoToAddress(
                                Optional.ofNullable(
                                        patientDto.address()
                                )
                        )
                )
                .personalDetail(
                        getPersonalDetail(
                                Optional.ofNullable(
                                        patientDto.personal_details()
                                )
                        )
                )
                .patientNextOfKinList(
                        getPatientNextKins(patientDto.next_of_kins())
                )
                .build();
    }

    private PersonalDetail getPersonalDetail(Optional<PersonalDetailDto> personalDetail) {
        PersonalDetailDto det = personalDetail.get();
        PersonalDetail theDetail = Optional.of(personalDetail.get())
                .filter(detail -> personalDetail.isPresent())
                .map(detail -> {
                    PersonalDetail conversion = PersonalDetail.builder()
                            .genoType(det.genoType())
                            .weight(det.weight())
                            .bloodGroup(det.bloodGroup())
                            .build();
                    return conversion;
                })
                .orElse(
                        null
                );
        return theDetail;
    }

    private List<HealthRecord> getAllRecords(List<HealthRecordDto> theRecords) {
        List<HealthRecord> allRecords = new ArrayList<>();
        if(!theRecords.isEmpty()) {
            allRecords = theRecords.stream()
                    .map(record -> convertFromHealthRecordDtotoHealthRecord(record))
                    .collect(Collectors.toList());
        }
        return allRecords;
    }

    private HealthRecord convertFromHealthRecordDtotoHealthRecord(HealthRecordDto theRecord) {
        HealthRecordDto rec;
        HealthRecord record = HealthRecord.builder()
                .note(theRecord.note())
                .time(theRecord.time())
                .build();
        return record;
    }

    private List<PatientNextOfKin> getPatientNextKins(List<NextOfKinDto> theNextOfKins) {
        List<PatientNextOfKin> allNextOfKins = new ArrayList<>();
        if(!theNextOfKins.isEmpty()) {
            allNextOfKins = theNextOfKins.stream()
                    .map(kin -> convertFromPatientNextofKinDtoToPatientNextOfKin(kin))
                    .collect(Collectors.toList());
        }
        return allNextOfKins;
    }

    private PatientNextOfKin convertFromPatientNextofKinDtoToPatientNextOfKin(NextOfKinDto nextOfKinDto) {
        PatientNextOfKin theNextOfKin = PatientNextOfKin.builder()
                .person(
                        buildPerson(
                                nextOfKinDto.first_name(), nextOfKinDto.last_name(), nextOfKinDto.gender()
                        )
                )
                .address(
                        convertFromAddressDtoToAddress(Optional.ofNullable(nextOfKinDto.address()))
                )
                .build();

        return theNextOfKin;
    }
}
