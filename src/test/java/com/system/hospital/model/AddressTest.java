package com.system.hospital.model;

import com.system.hospital.dto.AddressDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AddressTest {

    @Test
    public void test1() {
        var address = Address.builder()
                .houseNumber(3)
                .street("Kasana")
                .state("Abuja")
                .build();

        var addressDto = AddressDto.builder()
                .houseNumber(8)
                .build();
        address.updateAddress(addressDto);

        Assertions.assertThat(address.getHouseNumber()).isNotEqualTo(3);
        Assertions.assertThat(address.getHouseNumber()).isEqualTo(8);
        Assertions.assertThat(address.getStreet()).isEqualTo("Kasana");
        Assertions.assertThat(address.getState()).isEqualTo("Abuja");
        Assertions.assertThat(address.getId()).isEqualTo(0L);
    }
}
