package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.jackson.CustomDeserializer;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressDto(
        int houseNumber,
        @JsonDeserialize(using = CustomDeserializer.class)
        String street,
        @JsonDeserialize(using = CustomDeserializer.class)
        String state
) {
}
