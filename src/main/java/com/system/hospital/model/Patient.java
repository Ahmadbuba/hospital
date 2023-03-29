package com.system.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name= "patient")
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_id")
	private Person patientDetail;

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
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "next_of_kin_id")
	private List<NextOfKin> nextOfKins;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "patient",
			cascade = CascadeType.ALL
	)
	private List<HealthRecord> records;


}
