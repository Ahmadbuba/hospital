package com.system.hospital.model;

import com.system.hospital.dto.PersonalDetailDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

public class PersonalDetailTest {

    @Test
    public void test1() {
        var personalDetail = PersonalDetail.builder()
                .bloodGroup("0")
                .genoType("AS")
                .weight(55)
                .build();
        var personalDetailDto = PersonalDetailDto.builder()
                .weight(69)
                .bloodGroup("A+")
                .build();
        personalDetail.updatePersonalDetail(Optional.of(personalDetailDto));
        Assertions.assertThat(personalDetail.getWeight()).isNotEqualTo(55);
        Assertions.assertThat(personalDetail.getWeight()).isEqualTo(69);
        Assertions.assertThat(personalDetail.getGenoType()).isEqualTo("AS");
        Assertions.assertThat(personalDetail.getBloodGroup()).isEqualTo("A+");
        Assertions.assertThat(personalDetail.getId()).isNull();
    }

}
