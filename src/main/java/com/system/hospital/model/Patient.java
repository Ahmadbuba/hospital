package com.system.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.system.hospital.dto.*;
import com.system.hospital.service.UtilityService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name= "patient")
@Builder @Getter @NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Patient extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
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

    public void setPatientNextOfKinListNull() {
        this.patientNextOfKinList = null;
    }
    public void updatePatient(PatientDto patientDto) {
        Person thePerson = Person.builder().build();
        thePerson.updatePerson(
                patientDto.first_name(),
                Optional.ofNullable(patientDto.last_name()),
                patientDto.gender()
        );
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
                PatientNextOfKin patientNextOfKin = buildPatientNextOfKin(patientNextOfKinDto);
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