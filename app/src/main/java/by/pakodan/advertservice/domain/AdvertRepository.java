package by.pakodan.advertservice.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AdvertRepository {

    Optional<Advert> findSame(Address address, Set<PhoneNumberId> phoneNumberIds);

    List<Advert> findActive(LocalDate date);

    Advert save(Advert advert);
}
