package com.system.hospital.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientResponseDto;
import com.system.hospital.exception.ResourceNotFoundException;
import com.system.hospital.model.Gender;
import com.system.hospital.model.Patient;
import com.system.hospital.model.Person;
import com.system.hospital.repository.PatientRepository;
import com.system.hospital.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PatientController {
	private final PatientService patientService;

	@PostMapping("/patients")
	public ResponseEntity<String> createPatient(@Valid @RequestBody PatientDto patientDto) {
		patientService.createPatient(patientDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/patients")
	public ResponseEntity<List<PatientResponseDto>> getPatients(@RequestParam(required = false) String firstName) {
		List<PatientResponseDto> patients = patientService.getAllPatients(Optional.ofNullable(firstName));
		
		if(patients.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(patients, HttpStatus.OK);
	}
	
	@GetMapping("/patients/{id}")
	public ResponseEntity<PatientResponseDto> patientDetail(@PathVariable("id") long id) {
		return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
	}
	
	@PutMapping("/patients/{id}")
	public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable("id") long id, @Valid @RequestBody PatientDto patientDto, BindingResult bindingResult) {
		return new ResponseEntity<>(patientService.updatePatient(patientDto,id), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable("id") long id) {
		patientService.deletePatient(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/patients/{id}/address")
	public ResponseEntity<String> addAddress(@PathVariable("id")long patientId, AddressDto addressDto) {
		return null;
	}

}
