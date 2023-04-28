package com.system.hospital.model;

import java.util.List;


import jakarta.persistence.*;
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
