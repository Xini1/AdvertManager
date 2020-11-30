package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Advert {

    private final Address address;
    private final LocalDate creationDate;
    private EntityId id;
    private AdvertDescription advertDescription;
    private Price price;
    private Set<PhoneNumber> phoneNumbers;
    private LocalDate lastModificationDate;

    static Advert of(Address address,
                     AdvertDescription advertDescription,
                     Price price,
                     Set<PhoneNumber> phoneNumbers,
                     LocalDate creationDate) {

        Preconditions.checkNotNull(address, "address");
        Preconditions.checkNotNull(advertDescription, "advert description");
        Preconditions.checkNotNull(price, "price");
        Preconditions.checkNotNull(phoneNumbers, "phone numbers");
        Preconditions.checkSizeGreaterThan(phoneNumbers, 0, "phone numbers");
        Preconditions.checkNotNull(creationDate, "creation date");

        return new Advert(address,
                creationDate,
                null,
                advertDescription,
                price,
                phoneNumbers,
                null);
    }
}
