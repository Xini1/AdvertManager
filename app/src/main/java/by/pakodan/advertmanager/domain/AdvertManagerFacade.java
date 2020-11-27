package by.pakodan.advertmanager.domain;

import by.pakodan.advertmanager.domain.dto.AdvertDto;
import by.pakodan.advertmanager.domain.dto.SaveAdvertCommand;
import by.pakodan.advertmanager.domain.exception.AdvertNotFoundException;
import lombok.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class AdvertManagerFacade {

    private final AdvertRepository advertRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final AdvertFactory advertFactory;

    public long createOrUpdate(SaveAdvertCommand command) {
        validate(command);

        Set<PhoneNumber> phoneNumbers = getPhoneNumbers(command.getPhoneNumberStrings());

        Advert advertForSave = advertRepository.findByAddressAndPhoneNumbers(getAddressFrom(command), phoneNumbers)
                .map(advert -> advertFactory.updateAdvert(advert, command, phoneNumbers))
                .orElse(advertFactory.constructAdvert(command, phoneNumbers));

        return advertRepository.save(advertForSave).getId();
    }

    public AdvertDto getById(long id) {
        return advertRepository.findById(id)
                .map(Advert::toAdvertDto)
                .orElseThrow(() -> new AdvertNotFoundException(id));
    }

    private void validate(SaveAdvertCommand command) {
        new Validator<>(command)
                .validate(c -> Objects.nonNull(c.getUrlString()), "Url must not be null")
                .validate(c -> Objects.nonNull(c.getCity()), "City must not be null")
                .validate(c -> Objects.nonNull(c.getDistrict()), "District must not be null")
                .validate(c -> Objects.nonNull(c.getStreet()), "Street must not be null")
                .validate(c -> c.getHouseNumber() > 0, "House number must be positive")
                .validate(c -> c.getLevel() > 0, "Level must be positive")
                .validate(c -> c.getLevel() > 0, "Level must be positive")
                .validate(c -> !c.getPhoneNumberStrings().isEmpty(), "At least one phone number required")
                .errorHandler()
                .ifPresentThrowException();
    }

    private Set<PhoneNumber> getPhoneNumbers(Set<String> phoneNumberStrings) {
        return phoneNumberStrings.stream()
                .map(phoneNumberString -> phoneNumberRepository.findByStringRepresentation(phoneNumberString)
                        .orElse(phoneNumberRepository.save(PhoneNumber.from(phoneNumberString))))
                .collect(Collectors.toSet());
    }

    private Address getAddressFrom(SaveAdvertCommand command) {
        return Address.builder()
                .city(command.getCity())
                .district(command.getDistrict())
                .street(command.getStreet())
                .houseNumber(command.getHouseNumber())
                .level(command.getLevel())
                .build();
    }
}
