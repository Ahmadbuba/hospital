package com.system.hospital.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private final PatientRepository patientRepo;
	private final PatientService patientService;

	@PostMapping("/patients")
	public ResponseEntity<String> createPatient(@Valid @RequestBody PatientDto patientDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
		}
		patientService.createPatient(patientDto);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/patients")
	public ResponseEntity<List<PatientResponseDto>> getAllPatients(@RequestParam(required = false) String firstName) {
		List<Patient> patients = new ArrayList<Patient>();

		patients = Optional.ofNullable(firstName)
				.filter(name -> !firstName.isEmpty())
				.map(name -> patientRepo.findByPersonFirstName(name))
				.orElseGet(patientRepo::findAll);
		
		if(patients.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List <PatientResponseDto> allPatients = patients.stream()
				.map(patient -> convertToPatientResponseDto(patient))
				.collect(Collectors.toList());
		return new ResponseEntity<>(allPatients, HttpStatus.OK);
	}
	
	@GetMapping("/patients/{id}")
	public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable("id") long id) {
		PatientResponseDto patient = patientRepo.findById(id)
				.map(p -> convertToPatientResponseDto(p))
				.orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));
		
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}
	
	@PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable("id") long id, @Valid @RequestBody PatientDto patientDto) {
		Patient thePatient = patientRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Patient with id =" + id));


		
		return new ResponseEntity<>(patientRepo.save(thePatient), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Patient> deletePatient(@PathVariable("id") long id) {
		// should i add orElseThrow with ResourceNotFoundException here?
		patientRepo.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private String convertEnumToString(Gender value) {
		String res = value.toString().toLowerCase();
		res = res.substring(0,1).toUpperCase() + res.substring(1);
		return res;
	}

    private PatientResponseDto convertToPatientResponseDto(Patient patient) {
		PatientResponseDto thePatient = PatientResponseDto.builder()
				.id(patient.getId())
				.firstName(patient.getPerson().getFirstName())
				.lastName(patient.getPerson().getLastName())
				.gender(convertEnumToString(patient.getPerson().getGender()))
				.build();
		return thePatient;
	}

}
