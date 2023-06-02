package com.system.hospital.model;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.dto.PersonDto;
import com.system.hospital.service.UtilityService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name="patient_next_of_kin")
@Builder
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

    void updatePatientNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
        this.person.updatePerson(patientNextOfKinDto.first_name(),Optional.ofNullable(patientNextOfKinDto.last_name()), patientNextOfKinDto.gender());
        Optional.ofNullable(patientNextOfKinDto.address())
                .ifPresent(address -> {
                    var theAddress = Address.builder().build();
                    theAddress.updateAddress(patientNextOfKinDto.address());
                    this.address = theAddress;
                });

    }
    public void updateNextOfKinPatient(Patient patient) {
        this.patient = patient;
    }


}
