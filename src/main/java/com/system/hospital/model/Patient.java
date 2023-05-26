package com.system.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.system.hospital.dto.*;
import com.system.hospital.service.UtilityService;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @JsonIgnore
    private PersonalDetail personalDetail;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "patient"
    )
    @JsonIgnore
    private List<PatientNextOfKin> patientNextOfKinList;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "patient",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<HealthRecord> records;

    public void updatePatient(PatientDto patientDto) {
        this.person.updatePerson(UtilityService.buildPersonDto(patientDto));
        this.updateTheAddress(Optional.ofNullable(patientDto.address()));
        this.thePersonalDetail(Optional.ofNullable(patientDto.personal_details()));
        this.updateTheKins(Optional.ofNullable(patientDto.next_of_kins()));
    }

   private void updateTheAddress(Optional<AddressDto> myAddressDto) {
        myAddressDto.ifPresent(add -> {
            AddressDto addressDto = myAddressDto.get();
            if (this.address != null) {
                this.address.updateAddress(addressDto);
            } else  {
                this.address = UtilityService.buildAddress(myAddressDto);
            }
        });
    }

    private void thePersonalDetail(Optional<PersonalDetailDto> thePersonalDetail) {
        if (this.personalDetail != null) {
            this.personalDetail.updatePersonalDetail(thePersonalDetail);
        } else  {
            this.personalDetail = UtilityService.buildPersonalDetail(thePersonalDetail);
        }
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

    public void setNextOfKins(List<PatientNextOfKin> theList) {
        if (patientNextOfKinList == null) {
            patientNextOfKinList = new ArrayList<>();
        }
        for (PatientNextOfKin patientNextOfKin : theList) {
            patientNextOfKinList.add(patientNextOfKin);
        }
    }


}