package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.jackson.CustomDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;

import java.util.List;

public record PatientUpdateDto(
        @NotBlank @JsonDeserialize(using = CustomDeserializer.class)
        String first_name,
        @NotBlank @JsonDeserialize(using = CustomDeserializer.class)
        String last_name,
        @Pattern(regexp = "^(?i)(male|female)$", message = "Gender must either be 'male' or 'female'")
        String gender,
        AddressDto address,
        PersonalDetailDto personal_details,
        List<PatientNextOfKinDto> next_of_kins
) {
}
