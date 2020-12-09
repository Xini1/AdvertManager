package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Description {

    private final int rooms;
    private final int area;
    private final String information;

    static Description of(int rooms, int area, String information) {
        Preconditions.checkValueGreaterThan(rooms, 0, "rooms");
        Preconditions.checkValueGreaterThan(area, 0, "area");
        Preconditions.checkNotBlank(information, "advert description");

        return new Description(rooms, area, information);
    }
}
