package com.system.hospital.dto;

import jakarta.persistence.Column;

public record PersonalDetailDto(
        double weight,
        String bloodGroup,
       String genoType
) {
}
