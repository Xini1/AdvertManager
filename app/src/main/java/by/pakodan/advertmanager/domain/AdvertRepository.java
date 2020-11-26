package by.pakodan.advertmanager.domain;

import java.util.Optional;
import java.util.Set;

public interface AdvertRepository {

    Optional<Advert> findById(long id);

    Optional<Advert> findByAddressAndPhoneNumbers(Address address, Set<PhoneNumber> phoneNumbers);

    Advert save(Advert advert);
}
