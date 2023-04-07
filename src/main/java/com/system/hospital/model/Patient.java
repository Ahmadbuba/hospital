package com.system.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;


@Entity
@Table(name= "patient")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;

	@NotBlank(message = "First name cannot be empty")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	private String lastName;


	@Enumerated(EnumType.STRING)
	@NotEmpty
	private Gender gender;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "personal_detail_id")
	private PersonalDetail personalDetail;

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "patient"
	)
	private List<PatientNextOfKin> patientnextOfKins;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "patient",
			cascade = CascadeType.ALL
	)
	private List<HealthRecord> records;


}
