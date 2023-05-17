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
    private final  PatientNextOfKinRepository patientNextOfKinRepository;
    @Override
    @Transactional
    public long createPatient(PatientDto patientDto) {
        Patient patient = UtilityService.convertPatientDtoToPatient(patientDto);
        patientRepository.save(patient);
        for (PatientNextOfKin patientNextOfKin : patient.getPatientNextOfKinList()) {
            patientNextOfKin.updateNextOfKinPatient(patient);
            patientNextOfKinRepository.save(patientNextOfKin);
        }
        return patient.getId();
    }
    @Override
    public List<PatientResponse> getAllPatients(Optional<String> firstName) {
        List<PatientResponse> conversions = new ArrayList<>();
        String searchName = firstName.isPresent()? firstName.get() : null;
        List<Patient> thePatients = Optional.ofNullable(searchName)
                .filter(theSearchName -> searchName!=null)
                .map(theSearchName -> patientRepository.findByPersonFirstName(searchName))
                .orElse(patientRepository.findAll());

        if (!thePatients.isEmpty()) {
            conversions = thePatients.stream()
                    .map(patient -> UtilityService.convertFromPatientToPatientResponse(patient))
                    .collect(Collectors.toList());
        }
        return conversions;
    }

    @Override
    public PatientResponse getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
        return UtilityService.convertFromPatientToPatientResponse(patient);
    }

    @Override
    @Transactional
    public long updatePatient(PatientDto patientDto, long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
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

}
