package by.pakodan.advertservice.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AdvertRepository {

    Optional<Advert> findById(long id);

    Optional<Advert> findByAddressAndPhoneNumbers(Address address, Set<String> phoneNumberStrings);

    List<Advert> findByLastModificationDateIsAfter(LocalDate date);

    Advert save(Advert advert);
}
