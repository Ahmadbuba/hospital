package com.system.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="patient_next_of_kin")
@Getter @NoArgsConstructor @AllArgsConstructor
public class PatientNextOfKin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "firstName", column = @Column(name =
                    "first_name")),
            @AttributeOverride( name = "lastName", column = @Column(name =
                    "last_name")),
            @AttributeOverride( name = "gender", column = @Column(name =
                    "gender", nullable = false))

    })
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
