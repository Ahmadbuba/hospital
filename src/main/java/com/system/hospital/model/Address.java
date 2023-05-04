package com.system.hospital.model;

import jakarta.persistence.*;
import lombok.*;

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

}
