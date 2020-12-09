package by.pakodan.advertservice.domain;

import java.util.Optional;

public interface PhoneNumberRepository {

    Optional<PhoneNumber> findById(PhoneNumberId id);

    Optional<PhoneNumber> findByRawPhoneNumber(String rawPhoneNumber);

    PhoneNumber save(PhoneNumber phoneNumber);
}
