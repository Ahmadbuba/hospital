package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.jackson.CustomDeserializer;
import com.system.hospital.model.Gender;
import jakarta.validation.constraints.NotBlank;

public record NextOfKinDto(
        @NotBlank @JsonDeserialize(using = CustomDeserializer.class)
        String first_name,
        @JsonDeserialize(using = CustomDeserializer.class)
        String last_name,
        @ValueOfEnum(enumClass = Gender.class)
        String gender,
        AddressDto address
) {
}
