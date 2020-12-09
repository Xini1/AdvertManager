package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertId {

    @Getter
    private final String value;

    static AdvertId of(String value) {
        Preconditions.checkUUID(value, "advert id");

        return new AdvertId(value);
    }

    static AdvertId newInstance() {
        return of(UUID.randomUUID().toString());
    }
}
