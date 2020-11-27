package by.pakodan.advertservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
class PhoneNumber {

    private final Long id;
    private final String countryPrefix;
    private final String areaCode;
    private final String number;
    private final boolean isActive;
    private final LocalDate lastAdvertisementDate;

    static PhoneNumber from(String phoneNumber) {
        return PhoneNumber.builder()
                .countryPrefix(phoneNumber.substring(0, 4))
                .areaCode(phoneNumber.substring(4, 6))
                .number(phoneNumber.substring(6))
                .isActive(true)
                .build();
    }

    String asString() {
        return countryPrefix + areaCode + number;
    }
}
