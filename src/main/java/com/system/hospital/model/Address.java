package com.system.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="address")
@Getter @NoArgsConstructor @AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "state")
    private String state;

}
