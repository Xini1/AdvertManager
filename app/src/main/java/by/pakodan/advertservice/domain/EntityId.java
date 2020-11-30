package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class EntityId {

    private final long value;

    static EntityId of(long value) {
        Preconditions.checkValueGreaterThan(value, 0, "entity id");

        return new EntityId(value);
    }
}
