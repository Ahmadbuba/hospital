package com.system.hospital.model;

import java.util.List;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="doctor")
@Data @NoArgsConstructor @AllArgsConstructor
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_id")
	private Person doctorDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JoinColumn(name = "next_of_kin_id")
	private NextOfKin nextOfKin;

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
}
