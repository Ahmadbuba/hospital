package com.system.hospital.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PatientResponse(
        long id,
        String firstName,
        String lastName,
        String gender,
        AddressDto address,
        PersonalDetailDto details,
        List<PatientNextOfKinResponse> next_of_kins,
        List<HealthRecordResponse> records
) {
}
