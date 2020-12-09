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
    private final int totalLevels;

    static Address of(String city, String street, int house, int level, int totalLevels) {
        Preconditions.checkNotBlank(city, "city");
        Preconditions.checkNotBlank(street, "street");
        Preconditions.checkValueGreaterThan(house, 0, "house number");
        Preconditions.checkValueGreaterThan(level, 0, "level");
        Preconditions.checkValueGreaterThan(totalLevels, level - 1, "levels total");

        return new Address(city, street, house, level, totalLevels);
    }

    String asString() {
        return String.format("%s, %s, дом %d, этаж %d/%d", city, street, house, level, totalLevels);
    }
}
