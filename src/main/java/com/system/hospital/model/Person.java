package com.system.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="person")
@Getter @NoArgsConstructor @AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name="first_name", nullable=false)
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @Column(name="last_name", nullable=false)
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private Gender gender;
}
