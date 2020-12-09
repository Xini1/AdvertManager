package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.PhoneNumberUtils;
import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Clock;
import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class PhoneNumber {

    @Getter(AccessLevel.PACKAGE)
    private final PhoneNumberId id;
    private final String countryPrefix;
    private final String areaCode;
    private final String number;
    private final Clock clock;
    private boolean isActive;
    private LocalDate lastAdvertisementDate;

    static PhoneNumber of(String rawPhoneNumber, Clock clock) {
        Preconditions.checkNotBlank(rawPhoneNumber, "raw phone number");

        String cleanPhoneNumber = PhoneNumberUtils.formatPhoneNumber(rawPhoneNumber);

        Preconditions.checkMatchRegex(cleanPhoneNumber, "\\+375\\d{9}", "phone number");

        return new PhoneNumber(PhoneNumberId.newInstance(),
                cleanPhoneNumber.substring(0, 4),
                cleanPhoneNumber.substring(4, 6),
                cleanPhoneNumber.substring(6),
                clock,
                true,
                null);
    }

    PhoneNumber updateAdvertisementDate() {
        lastAdvertisementDate = LocalDate.now(clock);

        return this;
    }

    boolean isReadyForAdvertisement(LocalDate threshold) {
        return lastAdvertisementDate.isBefore(threshold);
    }

    String asRawPhoneNumber() {
        return countryPrefix + areaCode + number;
    }
}
