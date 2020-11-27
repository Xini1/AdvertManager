package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.domain.exception.PhoneNumberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
class InMemoryAdvertRepository implements AdvertRepository {

    private final Map<Long, Advert> map = new ConcurrentHashMap<>();
    private final PhoneNumberRepository phoneNumberRepository;
    private long nextFreeId = 1;

    @Override
    public Optional<Advert> findById(long id) {
        log.debug("findById");

        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Advert> findByAddressAndPhoneNumbers(Address address, Set<String> phoneNumberStrings) {
        log.debug("findByAddressAndPhoneNumbers");

        return map.values().stream()
                .filter(advert -> Objects.equals(advert.getAddress(), address))
                .filter(advert -> advert.getPhoneNumberIds().stream()
                        .map(id -> phoneNumberRepository.findById(id)
                                .orElseThrow(() -> new PhoneNumberNotFoundException(id)))
                        .anyMatch(phoneNumber -> phoneNumberStrings.contains(phoneNumber.asString())))
                .findAny();
    }

    @Override
    public List<Advert> findByLastModificationDateIsAfter(LocalDate date) {
        return map.values().stream()
                .filter(advert -> (Objects.nonNull(advert.getLastModificationDate()) &&
                        advert.getLastModificationDate().isAfter(date)) ||
                        advert.getCreationDate().isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public Advert save(Advert advert) {
        log.debug("save");

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
