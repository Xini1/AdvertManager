package by.pakodan.advertservice.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryPhoneNumberRepository implements PhoneNumberRepository {

    private final Map<Long, PhoneNumber> map = new ConcurrentHashMap<>();
    private long nextFreeId = 1;

    @Override
    public Optional<PhoneNumber> findByStringRepresentation(String phoneNumberString) {
        return map.values().stream()
                .filter(phoneNumber -> Objects.equals(phoneNumberString, phoneNumber.asString()))
                .findAny();
    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
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
