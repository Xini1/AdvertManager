package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class AdvertDescription {

    private final String value;

    static AdvertDescription of(String value) {
        Preconditions.checkNotBlank(value, "advert description");

        return new AdvertDescription(value);
    }
}
