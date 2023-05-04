package com.system.hospital.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name= "patient")
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride( name = "firstName", column = @Column(name =
					"first_name")),
			@AttributeOverride( name = "lastName", column = @Column(name =
					"last_name")),
			@AttributeOverride( name = "gender", column = @Column(name =
					"gender", nullable = false))

	})
	private Person person;

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
	private List<PatientNextOfKin> patientNextOfKinList;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "patient",
			cascade = CascadeType.ALL
	)
	private List<HealthRecord> records;


}
