package by.pakodan.advertmanager.domain;

import java.util.Optional;

public interface PhoneNumberRepository {

    Optional<PhoneNumber> findByStringRepresentation(String phoneNumberString);

    PhoneNumber save(PhoneNumber phoneNumber);
}
