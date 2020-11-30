package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Url {

    private final String value;

    static Url of(String value) {
        Preconditions.checkNotBlank(value, "url");

        return new Url(value);
    }
}
