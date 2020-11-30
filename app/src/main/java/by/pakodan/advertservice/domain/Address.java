package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Address {

    private final String city;
    private final String street;
    private final int house;
    private final int level;

    static Address of(String city, String street, int house, int level) {
        Preconditions.checkNotBlank(city, "city");
        Preconditions.checkNotBlank(street, "street");
        Preconditions.checkValueGreaterThan(house, 0, "house number");
        Preconditions.checkValueGreaterThan(level, 0, "level");

        return new Address(city, street, house, level);
    }
}
