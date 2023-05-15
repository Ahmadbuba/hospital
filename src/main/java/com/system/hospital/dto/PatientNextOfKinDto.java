package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.jackson.CustomDeserializer;
import com.system.hospital.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record PatientNextOfKinDto(
        @NotNull @NotBlank @JsonDeserialize(using = CustomDeserializer.class)
        String first_name,
        @JsonDeserialize(using = CustomDeserializer.class)
        String last_name,
        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?i)(male|female)$", message = "Gender must either be 'male' or 'female'")
        @JsonDeserialize(using = CustomDeserializer.class)
        String gender,
        AddressDto address
) {
}
