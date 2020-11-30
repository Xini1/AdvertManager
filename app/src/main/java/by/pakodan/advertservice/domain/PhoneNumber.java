package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
class PhoneNumber {

    private final String countryPrefix;
    private final String areaCode;
    private final String number;
    @EqualsAndHashCode.Exclude
    private EntityId id;
    private boolean isActive;
    private LocalDate lastAdvertisementDate;

    static PhoneNumber of(String rawPhoneNumber) {
        Preconditions.checkNotBlank(rawPhoneNumber, "raw phone number");

        String cleanPhoneNumber = rawPhoneNumber.replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replaceFirst("80", "+375");

        Preconditions.checkMatchRegex(cleanPhoneNumber, "+375\\d{8}", "phone number");

        return new PhoneNumber(cleanPhoneNumber.substring(0, 4),
                cleanPhoneNumber.substring(4, 6),
                cleanPhoneNumber.substring(6),
                null,
                true,
                null);
    }
}
