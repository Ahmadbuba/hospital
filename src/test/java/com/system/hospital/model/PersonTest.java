package com.system.hospital.model;

import com.system.hospital.dto.PersonDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PersonTest {

//    @Test
//    public void test1() {
//        var person = Person.builder()
//                .firstName("Jamila")
//                .lastName("Isa")
//                .gender(Gender.FEMALE)
//                .build();
//        var personDto = PersonDto.builder()
//                .firstName("Rahma")
//                .
//                .build();
//        person.updatePerson;
//        Assertions.assertThat(person.getFirstName()).isEqualTo("Rahma");
//        Assertions.assertThat(person.getFirstName()).isNotEqualTo("Jamila");
//        Assertions.assertThat(person.getLastName()).isEqualTo("Isa");
//        Assertions.assertThat(person.getGender()).isEqualTo(Gender.FEMALE);
//    }
//
//    @Test
//    public void test2() {
//        var person = Person.builder()
//                .firstName("Jamila")
//                .lastName("Isa")
//                .gender(Gender.FEMALE)
//                .build();
//        var personDto = PersonDto.builder()
//                .firstName("Ahmad")
//                .lastName("Buba")
//                .gender(Gender.MALE)
//                .build();
//        person.updatePerson(personDto);
//        Assertions.assertThat(person.getFirstName()).isEqualTo("Ahmad");
//        Assertions.assertThat(person.getFirstName()).isNotEqualTo("Jamila");
//        Assertions.assertThat(person.getLastName()).isEqualTo("Buba");
//        Assertions.assertThat(person.getGender()).isEqualTo(Gender.MALE);
//    }

    @Test
    public void CheckPersonBuilder() {
        var p = Person.builder()
                .gender(Gender.UNASSIGNED)
                .build();
        Assertions.assertThat(p).isInstanceOf(Person.class);
    }

}
