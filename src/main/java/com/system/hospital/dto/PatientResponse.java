package com.system.hospital.dto;

import lombok.Builder;

@Builder
public record PatientResponse(
        long id,
        String firstName,
        String lastName,
        String gender
) {
}
