package by.pakodan.advertmanager.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryAdvertRepository implements AdvertRepository {

    private final Map<Long, Advert> map = new ConcurrentHashMap<>();
    private long nextFreeId = 1;

    @Override
    public Optional<Advert> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Advert> findByAddressAndPhoneNumbers(Address address, Set<PhoneNumber> phoneNumbers) {
        return map.values().stream()
                .filter(advert -> Objects.equals(advert.getAddress(), address))
                .filter(advert -> phoneNumbers.stream()
                        .anyMatch(phoneNumber -> advert.getPhoneNumbers().contains(phoneNumber)))
                .findAny();
    }

    @Override
    public Advert save(Advert advert) {
        Advert advertForSave = advert;

        if (Objects.isNull(advertForSave.getId())) {
            advertForSave = advertForSave.toBuilder()
                    .id(nextFreeId++)
                    .build();
        }

        map.put(advertForSave.getId(), advertForSave);

        return advertForSave;
    }
}
