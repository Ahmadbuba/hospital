package com.system.hospital.model;

import java.util.List;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="doctor")
@Getter @NoArgsConstructor @AllArgsConstructor
public class Doctor {
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

	@OneToOne(
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY
	)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToOne(
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY
	)
	@JoinColumn(name = "personal_detail_id")
	private PersonalDetail personalDetail;
	@OneToMany(
				mappedBy="doctor",
				fetch = FetchType.LAZY
	)
	private List<HealthRecord> healthRecords;

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "doctor"
	)
	private List<DoctorNextOfKin> doctorNextOfKins;
}
