package by.pakodan.advertservice.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
class InMemoryPhoneNumberRepository implements PhoneNumberRepository {

    private final Map<Long, PhoneNumber> map = new ConcurrentHashMap<>();
    private long nextFreeId = 1;

    @Override
    public Optional<PhoneNumber> findById(long id) {
        log.debug("findById");

        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<PhoneNumber> findByStringRepresentation(String phoneNumberString) {
        log.debug("findByStringRepresentation");

        return map.values().stream()
                .filter(phoneNumber -> Objects.equals(phoneNumberString, phoneNumber.asString()))
                .findAny();
    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        log.debug("save");

        PhoneNumber phoneNumberForSave = phoneNumber;

        if (Objects.isNull(phoneNumberForSave.getId())) {
            phoneNumberForSave = phoneNumberForSave.toBuilder()
                    .id(nextFreeId++)
                    .build();
        }

        map.put(phoneNumberForSave.getId(), phoneNumberForSave);

        return phoneNumberForSave;
    }
}
