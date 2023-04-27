package com.system.hospital.dto;

import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.model.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PatientDto(
        @NotBlank
        String firstName,
        String lastName,
        @ValueOfEnum(enumClass = Gender.class)
        String gender
) {
}
