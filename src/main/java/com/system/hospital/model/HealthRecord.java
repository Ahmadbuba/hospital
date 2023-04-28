package com.system.hospital.model;

import java.time.OffsetDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name="health_record")
@Data @NoArgsConstructor @AllArgsConstructor
public class HealthRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;
	
	@Column(name="date", nullable=false)
	private OffsetDateTime time;
	
	@Column(name="note", nullable=false)
	private String note;
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
	)
	@JoinColumn(
			name="patient_id",
			nullable=false
	)
	private Patient patient;
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
	)
	@JoinColumn(name="doctor_id", nullable=false)
	private Doctor doctor;
	
}
