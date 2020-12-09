package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.domain.dto.SaveAdvertCommand;
import by.pakodan.advertservice.utils.PhoneNumberUtils;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AdvertServiceFacade {

    private final AdvertRepository advertRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final Clock clock;
    private final int daysToWaitAfterAdvertCreation;
    private final int daysToWaitAfterLastAdvertisement;

    public void createOrUpdate(SaveAdvertCommand command) {
        Address address = Address.of(command.getCity(),
                command.getStreet(),
                command.getHouse(),
                command.getLevel(),
                command.getTotalLevels());

        Url url = Url.of(command.getUrlString());
        Description description = Description.of(command.getRooms(), command.getArea(), command.getDescription());
        Price price = Price.of(Currency.valueOf(command.getCurrencyString()), command.getAmountString());

        Set<PhoneNumberId> phoneNumberIds = command.getPhoneNumberStrings().stream()
                .map(PhoneNumberUtils::formatPhoneNumber)
                .map(rawPhoneNumber -> phoneNumberRepository.findByRawPhoneNumber(rawPhoneNumber)
                        .orElseGet(() -> phoneNumberRepository.save(PhoneNumber.of(rawPhoneNumber, clock))))
                .map(PhoneNumber::getId)
                .collect(Collectors.toSet());

        Advert advertForSaving = advertRepository.findSame(address, phoneNumberIds)
                .map(advert -> advert.changeUrl(url))
                .map(advert -> advert.changeDescription(description))
                .map(advert -> advert.changePrice(price))
                .map(advert -> advert.changePhoneNumberIds(phoneNumberIds))
                .orElseGet(() -> Advert.of(url, address, description, price, phoneNumberIds, clock));

        advertRepository.save(advertForSaving);
    }

    public Set<String> getPhoneNumbersForAdvertisement() {
        return advertRepository.findActive(LocalDate.now(clock).minusDays(daysToWaitAfterAdvertCreation)).stream()
                .flatMap(advert -> advert.getPhoneNumberIds().stream())
                .map(phoneNumberRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(phoneNumber -> phoneNumber.isReadyForAdvertisement(
                        LocalDate.now(clock).minusDays(daysToWaitAfterLastAdvertisement)))
                .map(PhoneNumber::updateAdvertisementDate)
                .map(phoneNumberRepository::save)
                .map(PhoneNumber::asRawPhoneNumber)
                .collect(Collectors.toSet());
    }
}
