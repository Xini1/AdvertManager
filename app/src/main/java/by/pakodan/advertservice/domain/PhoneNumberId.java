package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumberId {

    @Getter
    private final String value;

    static PhoneNumberId of(String value) {
        Preconditions.checkUUID(value, "phone number id");

        return new PhoneNumberId(value);
    }

    static PhoneNumberId newInstance() {
        return of(UUID.randomUUID().toString());
    }
}
