package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.jackson.CustomDeserializer;
import com.system.hospital.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Builder
public record PatientDto(
        @NotBlank @NonNull @JsonDeserialize(using = CustomDeserializer.class)
        String first_name,
        @JsonDeserialize(using = CustomDeserializer.class)
        String last_name,
        @NotBlank @NotNull
        @Pattern(regexp = "^(?i)(male|female)$", message = "Gender must either be 'male' or 'female'")
        String gender,
        AddressDto address,
        PersonalDetailDto personal_details,
        List<PatientNextOfKinDto> next_of_kins

) {
}
