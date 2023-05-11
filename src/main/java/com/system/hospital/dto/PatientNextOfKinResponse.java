package com.system.hospital.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.hospital.constraint.ValueOfEnum;
import com.system.hospital.jackson.CustomDeserializer;
import com.system.hospital.model.Gender;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PatientNextOfKinResponse(
        long id,
        String first_name,
        String last_name,
        String gender,
        AddressDto address
) {
}
