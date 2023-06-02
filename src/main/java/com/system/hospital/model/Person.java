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


   public void updatePerson(String theFirstName, Optional<String> theLastName, String theGender) {
       theLastName.ifPresent(lName -> this.lastName = lName);
       this.firstName = theFirstName;
       this.gender = buildGender(theGender);
    }

    private Gender buildGender(String theGender) {
       return theGender.equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE;
    }
}
