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

	public void updatePersonalDetail (Optional<PersonalDetailDto> personalDetailDto) {
		if (personalDetailDto.isPresent()) {
			PersonalDetailDto thePersonalDetailDto = personalDetailDto.get();
			Optional.ofNullable(thePersonalDetailDto.weight())
							.ifPresent(theWeight -> this.weight = thePersonalDetailDto.weight());
			Optional.ofNullable(thePersonalDetailDto.bloodGroup())
							.ifPresent(theBloodGroup -> this.bloodGroup = thePersonalDetailDto.bloodGroup());
			Optional.ofNullable(thePersonalDetailDto.genoType())
					.ifPresent(theGenotype -> this.genoType = thePersonalDetailDto.genoType());
		}
	}
}
