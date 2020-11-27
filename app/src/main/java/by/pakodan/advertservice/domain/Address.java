package by.pakodan.advertservice.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
class Address {

    private final String city;
    private final String district;
    private final String street;
    private final int houseNumber;
    private final int level;
}
