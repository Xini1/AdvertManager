package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.domain.dto.AdvertDto;
import by.pakodan.advertservice.domain.dto.GetActiveAdvertsPhoneNumbersQuery;
import by.pakodan.advertservice.domain.dto.SaveAdvertCommand;
import by.pakodan.advertservice.domain.exception.AdvertNotFoundException;
import by.pakodan.advertservice.domain.exception.PhoneNumberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class AdvertServiceFacade {

    private final AdvertRepository advertRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final AdvertFactory advertFactory;
    private final Clock clock;

    public long createOrUpdate(SaveAdvertCommand command) {
        log.debug("Validating command = {}", command);
        validate(command);

        log.debug("Constructing advert from command");
        Advert advertForSave = advertRepository.findByAddressAndPhoneNumbers(getAddressFrom(command),
                command.getPhoneNumberStrings())
                .map(advert -> advertFactory.updateAdvert(advert, command,
                        getPhoneNumbers(command.getPhoneNumberStrings())))
                .orElseGet(() -> advertFactory.constructAdvert(command,
                        getPhoneNumbers(command.getPhoneNumberStrings())));

        log.debug("Saving advert to database");
        return advertRepository.save(advertForSave).getId();
    }

    public AdvertDto getById(long id) {
        log.debug("Finding advert in database by id = {}", id);

        return advertRepository.findById(id)
                .map(this::toAdvertDto)
                .orElseThrow(() -> new AdvertNotFoundException(id));
    }

    public String getActiveAdvertsPhoneNumbersString(GetActiveAdvertsPhoneNumbersQuery query) {
        log.debug("Finding phone numbers to send advertisement: {}", query);

        return advertRepository.findByLastModificationDateIsAfter(query.getLastModificationDate())
                .stream()
                .flatMap(advert -> advert.getPhoneNumberIds().stream()
                        .map(id -> phoneNumberRepository.findById(id)
                                .orElseThrow(() -> new PhoneNumberNotFoundException(id)))
                        .filter(PhoneNumber::isActive)
                        .filter(phoneNumber -> Objects.isNull(phoneNumber.getLastAdvertisementDate()) ||
                                phoneNumber.getLastAdvertisementDate().isBefore(query.getLastAdvertisementDate()))
                        .findFirst()
                        .stream())
                .distinct()
                .map(phoneNumber -> phoneNumber.toBuilder()
                        .lastAdvertisementDate(LocalDate.now(clock))
                        .build())
                .map(phoneNumberRepository::save)
                .map(PhoneNumber::asString)
                .collect(Collectors.joining(";"));
    }

    private AdvertDto toAdvertDto(Advert advert) {
        return AdvertDto.builder()
                .id(advert.getId())
                .url(advert.getUrl().getValue())
                .city(advert.getAddress().getCity())
                .street(advert.getAddress().getStreet())
                .houseNumber(advert.getAddress().getHouseNumber())
                .level(advert.getAddress().getLevel())
                .amount(advert.getPrice().getAmount())
                .currency(advert.getPrice().getCurrency().name())
                .phoneNumbers(advert.getPhoneNumberIds().stream()
                        .map(id -> phoneNumberRepository.findById(id)
                                .orElseThrow(() -> new PhoneNumberNotFoundException(id)))
                        .map(PhoneNumber::asString)
                        .collect(Collectors.toSet()))
                .build();
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
                .sorted()
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
