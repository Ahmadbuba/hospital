package com.system.hospital.dto;

import com.system.hospital.model.Gender;
import lombok.Builder;

@Builder
public record PersonDto(
        String firstName,
        String lastName
) {
}
