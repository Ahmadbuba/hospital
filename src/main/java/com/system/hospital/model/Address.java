package com.system.hospital.model;

import com.system.hospital.dto.AddressDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Table(name="address")
@Builder
@Getter @NoArgsConstructor @AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "house_number")
    private int houseNumber;

    private String street;

    private String state;

    void updateAddress(AddressDto addressDto) {
            Optional.ofNullable(addressDto.houseNumber())
                    .map(theHouseNumber -> this.houseNumber = addressDto.houseNumber())
                    .orElse(this.houseNumber);
            Optional.ofNullable(addressDto.street())
                    .map(theStreet -> this.street = addressDto.street())
                    .orElse(this.street);
            Optional.ofNullable(addressDto.state())
                    .map(theState -> this.state = addressDto.state())
                    .orElse(this.state);
        }

}
