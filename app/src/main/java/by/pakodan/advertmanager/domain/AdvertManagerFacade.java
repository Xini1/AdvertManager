package by.pakodan.advertmanager.domain;

import by.pakodan.advertmanager.domain.dto.AdvertDto;
import by.pakodan.advertmanager.domain.dto.SaveAdvertCommand;
import by.pakodan.advertmanager.domain.exception.AdvertNotFoundException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class AdvertManagerFacade {

    private final AdvertRepository advertRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final AdvertFactory advertFactory;
    private final AdvertManagerProperties advertManagerProperties;

    public long createOrUpdate(SaveAdvertCommand command) {
        log.debug("Validating command = {}", command);
        validate(command);

        log.debug("Fetching phone numbers from database");
        Set<PhoneNumber> phoneNumbers = getPhoneNumbers(command.getPhoneNumberStrings());

        log.debug("Constructing advert from command");
        Advert advertForSave = advertRepository.findByAddressAndPhoneNumbers(getAddressFrom(command), phoneNumbers)
                .map(advert -> advertFactory.updateAdvert(advert, command, phoneNumbers))
                .orElseGet(() -> advertFactory.constructAdvert(command, phoneNumbers));

        log.debug("Saving advert to database");
        return advertRepository.save(advertForSave).getId();
    }

    public AdvertDto getById(long id) {
        log.debug("Finding advert in database by id = {}", id);

        return advertRepository.findById(id)
                .map(Advert::toAdvertDto)
                .orElseThrow(() -> new AdvertNotFoundException(id));
    }

    private void validate(SaveAdvertCommand command) {
        new ValidationExecutor<>(command)
                .validate()
                .errorHandler()
                .log()
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
