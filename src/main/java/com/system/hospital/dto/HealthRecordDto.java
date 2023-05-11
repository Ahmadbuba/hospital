package com.system.hospital.dto;

import com.system.hospital.model.Doctor;
import com.system.hospital.model.Patient;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.OffsetDateTime;
@Builder
public record HealthRecordDto(
        String note
) {
}
