package by.pakodan.advertmanager.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
class PhoneNumber {

    @EqualsAndHashCode.Exclude
    private final Long id;
    private final String countryPrefix;
    private final String areaCode;
    private final String number;
    private final LocalDate lastAdvertisementDate;

    static PhoneNumber from(String phoneNumber) {
        return PhoneNumber.builder()
                .countryPrefix(phoneNumber.substring(0, 4))
                .areaCode(phoneNumber.substring(4, 6))
                .number(phoneNumber.substring(6))
                .build();
    }

    String asString() {
        return countryPrefix + areaCode + number;
    }
}
