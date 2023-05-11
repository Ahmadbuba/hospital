package com.system.hospital.model;

import com.system.hospital.dto.AddressDto;
import com.system.hospital.dto.PatientDto;
import com.system.hospital.dto.PatientNextOfKinDto;
import com.system.hospital.dto.PersonDto;
import com.system.hospital.service.UtilityService;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name= "patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @NonNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name =
                    "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name =
                    "last_name")),
            @AttributeOverride(name = "gender", column = @Column(name =
                    "gender", nullable = false))

    })
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "personal_detail_id")
    private PersonalDetail personalDetail;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "patient"
    )
    private List<PatientNextOfKin> patientNextOfKinList;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "patient",
            cascade = CascadeType.ALL
    )
    private List<HealthRecord> records;

    public void updatePatient(PatientDto patientDto) {
        this.person.updatePerson(UtilityService.buildPersonDto(patientDto));
        this.updateTheAddress(Optional.ofNullable(this.address),Optional.ofNullable(patientDto.address()));
        this.personalDetail.updatePersonalDetail(Optional.ofNullable(patientDto.personal_details()));
        this.updateTheKins(Optional.ofNullable(patientDto.next_of_kins()));
    }

   private void updateTheAddress(Optional<Address> myAddress, Optional<AddressDto> myAddressDto) {
        myAddress.ifPresent(add -> {
            if (myAddress.isPresent()) {
                AddressDto theAdd = myAddressDto.get();
                this.address.updateAddress(theAdd);
            }
        });
    }

    private void updateTheKins(Optional<List<PatientNextOfKinDto>> patientNextOfKinDtoList) {
        if (patientNextOfKinDtoList.isPresent()) {
            List<PatientNextOfKinDto> holder = patientNextOfKinDtoList.get();
            List<PatientNextOfKin> theList = holder.stream()
                    .map(nextOfKinDto -> UtilityService.convertToNextOfKin(nextOfKinDto))
                    .collect(Collectors.toList());
            this.patientNextOfKinList = theList;
            this.patientNextOfKinList.stream()
                    .forEach(patientNextOfKin -> patientNextOfKin.updateNextOfKinPatient(this));
        }

    }

}