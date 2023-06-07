package com.system.hospital.service.impl;

import com.system.hospital.dto.*;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.*;
import com.system.hospital.repository.PatientNextOfKinRepository;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.PatientService;
import com.system.hospital.service.UtilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientNextOfKinRepository patientNextOfKinRepository;
    private final UtilityService utilityService;

    @Override
    @Transactional
    public long createPatient(PatientDto patientDto) {
        Patient patient = Patient.builder().build();
        patient.updatePatient(patientDto);
        patientRepository.save(patient);
        return patient.getId();
    }
    @Override
    public List<PatientResponse> getAllPatients(Optional<String> firstName) {
        String searchName = firstName.orElse(null);
        List<Patient> thePatients = searchName == null ? findAllPatients() : findPatientsByFirstName(searchName);
        return convertToPatientResponses(thePatients);
    }
    @Override
    public PatientResponse getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        return utilityService.convertFromPatientToPatientResponse(patient);
    }

    @Override
    @Transactional
    public long updatePatient(PatientDto patientDto, long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        boolean checkPatientDtoNextOfKins = patientDto.next_of_kins() != null && !patientDto.next_of_kins().isEmpty();
        boolean checkPatientNextOfKinList = patient.getPatientNextOfKinList() != null && !patient.getPatientNextOfKinList().isEmpty();

        if (checkPatientDtoNextOfKins && checkPatientNextOfKinList) {
            patientNextOfKinRepository.deleteAll(patient.getPatientNextOfKinList());
        }
        patient.updatePatient(patientDto);
        patientRepository.save(patient);
        return patient.getId();
    }

    @Override
    @Transactional
    public void deletePatient(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        patientRepository.delete(patient);
    }

    private List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    private List<Patient> findPatientsByFirstName(String firstName) {
        return patientRepository.findByPersonFirstName(firstName);
    }

    private List<PatientResponse> convertToPatientResponses(List<Patient> patients) {
        return patients.stream()
                .map(patient -> utilityService.convertFromPatientToPatientResponse(patient))
                .collect(Collectors.toList());
    }
}
