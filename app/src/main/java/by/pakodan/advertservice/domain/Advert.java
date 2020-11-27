package by.pakodan.advertservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
class Advert {

    private final Long id;
    private final URL url;
    private final Address address;
    private final String description;
    private final Price price;
    private final Set<Long> phoneNumberIds;
    private final LocalDate creationDate;
    private final LocalDate lastModificationDate;
}
