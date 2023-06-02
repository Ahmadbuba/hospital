package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.jackson.CustomDeserializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PersonalDetailDto(
        @NotNull
        double weight,
        @JsonDeserialize(using = CustomDeserializer.class)
        String bloodGroup,
        @JsonDeserialize(using = CustomDeserializer.class)
        String genoType
) {
}
