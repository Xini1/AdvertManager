package by.pakodan.advertmanager.domain;

import by.pakodan.advertmanager.domain.dto.AdvertDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Getter
class Advert {

    private final Long id;
    private final URL url;
    private final Address address;
    private final String description;
    private final Price price;
    private final Set<PhoneNumber> phoneNumbers;
    private final LocalDate creationDate;
    private final LocalDate lastModificationDate;

    AdvertDto toAdvertDto() {
        return AdvertDto.builder()
                .id(id)
                .url(url.getValue())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .level(address.getLevel())
                .amount(price.getAmount())
                .currency(price.getCurrency().name())
                .phoneNumbers(phoneNumbers.stream()
                        .map(PhoneNumber::asString)
                        .collect(Collectors.toSet()))
                .build();
    }
}
