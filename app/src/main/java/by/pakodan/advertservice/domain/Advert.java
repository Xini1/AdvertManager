package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Advert {

    private final AdvertId id;
    private final Address address;
    private final LocalDate creationDate;
    private final Clock clock;
    private Url url;
    private Description description;
    private Price price;
    private Set<PhoneNumberId> phoneNumberIdSet;
    private LocalDate lastModificationDate;

    static Advert of(Url url,
                     Address address,
                     Description description,
                     Price price,
                     Set<PhoneNumberId> phoneNumberIdSet,
                     Clock clock) {

        Preconditions.checkNotNull(url, "url");
        Preconditions.checkNotNull(address, "address");
        Preconditions.checkNotNull(description, "advert description");
        Preconditions.checkNotNull(price, "price");
        Preconditions.checkNotNull(phoneNumberIdSet, "phone numbers");
        Preconditions.checkSizeGreaterThan(phoneNumberIdSet, 0, "phone numbers");
        Preconditions.checkNotNull(clock, "clock");

        return new Advert(AdvertId.newInstance(),
                address,
                LocalDate.now(clock),
                clock,
                url,
                description,
                price,
                new HashSet<>(phoneNumberIdSet),
                null);
    }

    Set<PhoneNumberId> getPhoneNumberIds() {
        return new HashSet<>(phoneNumberIdSet);
    }

    Advert changeUrl(Url newUrl) {
        url = newUrl;
        lastModificationDate = LocalDate.now(clock);

        return this;
    }

    Advert changeDescription(Description newDescription) {
        description = newDescription;
        lastModificationDate = LocalDate.now(clock);

        return this;
    }

    Advert changePrice(Price newPrice) {
        price = newPrice;
        lastModificationDate = LocalDate.now(clock);

        return this;
    }

    Advert changePhoneNumberIds(Set<PhoneNumberId> newPhoneNumberIdSet) {
        phoneNumberIdSet = new HashSet<>(newPhoneNumberIdSet);
        lastModificationDate = LocalDate.now(clock);

        return this;
    }
}
