package com.system.hospital.model;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="personal_detail")
@Data @NoArgsConstructor @AllArgsConstructor
public class PersonalDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private Long id;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="blood_group")
	private String bloodGroup;
	
	@Column(name="genotype")
	private String genoType;
}
