package by.pakodan.advertmanager.domain;

import by.pakodan.advertmanager.domain.dto.SaveAdvertCommand;

import java.time.LocalDate;
import java.util.Set;

class AdvertFactory {

    Advert constructAdvert(SaveAdvertCommand command, Set<PhoneNumber> phoneNumbers) {
        return setCommonFiends(Advert.builder(), command, phoneNumbers)
                .address(Address.builder()
                        .city(command.getCity())
                        .district(command.getDistrict())
                        .street(command.getStreet())
                        .houseNumber(command.getHouseNumber())
                        .level(command.getLevel())
                        .build())
                .creationDate(LocalDate.now())
                .build();
    }

    Advert updateAdvert(Advert advert, SaveAdvertCommand command, Set<PhoneNumber> phoneNumbers) {
        return setCommonFiends(advert.toBuilder(), command, phoneNumbers)
                .lastModificationDate(LocalDate.now())
                .build();
    }

    private Advert.AdvertBuilder setCommonFiends(Advert.AdvertBuilder builder, SaveAdvertCommand command,
                                                 Set<PhoneNumber> phoneNumbers) {

        return builder
                .url(URL.builder()
                        .value(command.getUrlString())
                        .build())
                .description(command.getDescription())
                .price(Price.builder()
                        .amount(command.getAmount())
                        .currency(Currency.valueOf(command.getCurrencyString()))
                        .build())
                .phoneNumbers(phoneNumbers);
    }
}
