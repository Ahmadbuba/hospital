package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.model.Gender;
import com.system.hospital.jackson.CustomDeserializer;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatientDto(
        @JsonDeserialize(using = CustomDeserializer.class) @NotBlank
        String firstName,
        @JsonDeserialize(using = CustomDeserializer.class)
        String lastName,
        @ValueOfEnum(enumClass = Gender.class)
        String gender
) {
}
