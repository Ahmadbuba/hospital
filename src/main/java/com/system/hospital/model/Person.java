package com.system.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Embeddable
@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Person {
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;


    @Enumerated(EnumType.STRING)
    @NotEmpty
    private Gender gender;
}
