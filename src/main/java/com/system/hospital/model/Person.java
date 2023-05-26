package com.system.hospital.model;

import com.system.hospital.dto.PersonDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Optional;

@Embeddable
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Person {
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Gender gender;

    void updatePerson(PersonDto personDto) {
        Optional.ofNullable(personDto.firstName())
                .map(firstName -> this.firstName = personDto.firstName())
                .orElse(this.firstName);
        Optional.ofNullable(personDto.lastName())
                .map(lastName -> this.lastName = personDto.lastName())
                .orElse(this.lastName);
        Optional.ofNullable(personDto.gender())
                .map(gender -> this.gender = personDto.gender())
                .orElse(this.gender);
    }
}
