package com.system.hospital.dto;

import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.model.Gender;
import lombok.Builder;

@Builder
public record PatientResponseDto(
        long id,
        String firstName,
        String lastName,
        String gender
) {
}
