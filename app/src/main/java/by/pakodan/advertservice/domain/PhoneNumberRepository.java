package by.pakodan.advertservice.domain;

import java.util.Optional;

public interface PhoneNumberRepository {

    Optional<PhoneNumber> findById(long id);

    Optional<PhoneNumber> findByStringRepresentation(String phoneNumberString);

    PhoneNumber save(PhoneNumber phoneNumber);
}
