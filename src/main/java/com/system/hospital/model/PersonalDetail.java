package com.system.hospital.model;



import com.system.hospital.dto.PersonalDetailDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Table(name="personal_detail")
@Builder
@Getter @NoArgsConstructor @AllArgsConstructor
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


	void updatePersonalDetail(PersonalDetailDto personalDetailDto) {
		this.weight = personalDetailDto.weight();
		Optional.ofNullable(personalDetailDto.bloodGroup())
				.map(bloodGroup -> this.bloodGroup = personalDetailDto.bloodGroup())
				.orElse(this.bloodGroup);
		Optional.ofNullable(personalDetailDto.genoType())
				.map(genoType -> this.genoType = personalDetailDto.genoType())
				.orElse(this.genoType);
	}
}
