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

    public void updatePerson(Person thePerson) {
        this.person = thePerson;
    }

    public void setPatientNextOfKinListNull() {
        this.patientNextOfKinList = null;
    }
    public void updatePatient(PatientDto patientDto) {
        Person thePerson = Person.builder()
                .gender(Gender.UNASSIGNED)
                .build();
        thePerson.updatePerson(
                patientDto.first_name(),
                Optional.ofNullable(patientDto.last_name()),
                patientDto.gender()
        );
//        Person thePerson = UtilityService.getInstance()
//                .buildPerson(
//                        patientDto.first_name(),
//                        Optional.ofNullable(patientDto.last_name()),
//                        patientDto.gender()
//                );
        this.person = thePerson;
        this.updateTheAddress(Optional.ofNullable(patientDto.address()));
        this.updateThePersonalDetail(Optional.ofNullable(patientDto.personal_details()));
        this.updateTheKins(Optional.ofNullable(patientDto.next_of_kins()));
    }

   private void updateTheAddress(Optional<AddressDto> myAddressDto) {
        myAddressDto.ifPresent(addressDto -> {
            if (this.address != null) {
                this.address.updateAddress(addressDto);
            } else  {
                this.address = Address.builder().build();
                this.address.updateAddress(addressDto);
            }
        });
    }

//    private void thePersonalDetail(Optional<PersonalDetailDto> myPersonalDetail) {
//        if (this.personalDetail != null) {
//            this.personalDetail.updatePersonalDetail(thePersonalDetail);
//        } else  {
//            this.personalDetail = UtilityService.getInstance().buildPersonalDetail(thePersonalDetail);
//        }
//    }

    private void updateThePersonalDetail(Optional<PersonalDetailDto> myPersonalDetail) {
        myPersonalDetail.ifPresent(pDetail -> {
            if (this.personalDetail != null) {
                this.personalDetail.updatePersonalDetail(pDetail);
            } else {
                this.personalDetail = PersonalDetail.builder().build();
                this.personalDetail.updatePersonalDetail(pDetail);
            }
        });
    }

    private void updateTheKins(Optional<List<PatientNextOfKinDto>> patientNextOfKinDtoList) {
        patientNextOfKinDtoList.ifPresent(dtoList -> {
            List<PatientNextOfKin> patientNextOfKins = new ArrayList<>();
            for (PatientNextOfKinDto patientNextOfKinDto: dtoList) {
                var patientNextOfKin = buildPatientNextOfKin(patientNextOfKinDto);
                patientNextOfKin.updateNextOfKinPatient(this);
                patientNextOfKins.add(patientNextOfKin);
            }
            this.patientNextOfKinList = patientNextOfKins;
        });

    }

    private PatientNextOfKin buildPatientNextOfKin(PatientNextOfKinDto patientNextOfKinDto) {
        var thePerson = Person.builder()
                .gender(Gender.UNASSIGNED)
                .build();
        var patientNextOfKin = PatientNextOfKin.builder()
                .person(thePerson)
                .build();
        patientNextOfKin.updatePatientNextOfKin(patientNextOfKinDto);
        return patientNextOfKin;
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