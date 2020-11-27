package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.domain.dto.GetActiveAdvertsPhoneNumbersQuery;
import by.pakodan.advertservice.domain.dto.SaveAdvertCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GetActiveAdvertsPhoneNumbersTest {

    private AdvertServiceFacade advertServiceFacade;

    @BeforeEach
    void setUp() {
        advertServiceFacade = new AdvertServiceConfiguration()
                .advertServiceFacadeForTests(Clock.fixed(Instant.now().minus(10, ChronoUnit.DAYS),
                        ZoneId.systemDefault()));
    }

    @Test
    void givenNoAdverts_whenGetActiveAdvertsPhoneNumbers_thenReturnEmptyString() {
        String phoneNumbersString =
                advertServiceFacade.getActiveAdvertsPhoneNumbersString(getActiveAdvertsPhoneNumbersQuery());

        assertThat(phoneNumbersString).isEmpty();
    }

    @Test
    void givenAdvertsWithRepeatedNumbers_whenGetActiveAdvertsPhoneNumbers_thenReturnUniquePhoneNumbers() {
        Set<String> phoneNumberSet = Collections.singleton("+375291111111");
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(1, phoneNumberSet));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(2, phoneNumberSet));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(3, phoneNumberSet));

        String phoneNumbersString =
                advertServiceFacade.getActiveAdvertsPhoneNumbersString(getActiveAdvertsPhoneNumbersQuery());

        assertThat(phoneNumbersString).isNotBlank();
        assertThat(phoneNumbersString.split(";")).hasSize(1);
    }

    @Test
    void givenAdvertsWithDifferentNumbers_whenGetActiveAdvertsPhoneNumbers_thenReturn3PhoneNumbers() {
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(1, Collections.singleton("+375291111111")));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(2, Collections.singleton("+375292222222")));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(3, Collections.singleton("+375293333333")));

        String phoneNumbersString =
                advertServiceFacade.getActiveAdvertsPhoneNumbersString(getActiveAdvertsPhoneNumbersQuery());

        assertThat(phoneNumbersString).isNotBlank();
        assertThat(phoneNumbersString.split(";")).hasSize(3);
    }

    @Test
    void givenAdvertsWithMixedNumbers_whenGetActiveAdvertsPhoneNumbers_thenReturnUniquePhoneNumbers() {
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(1, Collections.singleton("+375291111111")));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(2, Set.of("+375292222222", "+375293333333")));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(3, Collections.singleton("+375293333333")));

        String phoneNumbersString =
                advertServiceFacade.getActiveAdvertsPhoneNumbersString(getActiveAdvertsPhoneNumbersQuery());

        assertThat(phoneNumbersString).isNotBlank();
        assertThat(phoneNumbersString.split(";")).hasSize(3);
        assertThat(Set.of(phoneNumbersString.split(";"))).hasSize(3);
    }

    @Test
    void givenAdvertsWithSeveralNumbers_whenGetActiveAdvertsPhoneNumbers_thenReturnAtMost1PhoneNumberPerAdvert() {
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(1, Set.of("+375291111111", "+375292222222")));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(2, Set.of("+375293333333", "+375294444444")));
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(3, Set.of("+375295555555", "+375296666666")));

        String phoneNumbersString =
                advertServiceFacade.getActiveAdvertsPhoneNumbersString(getActiveAdvertsPhoneNumbersQuery());

        assertThat(phoneNumbersString).isNotBlank();
        assertThat(phoneNumbersString.split(";")).hasSize(3);
        assertThat(Set.of(phoneNumbersString.split(";"))).hasSize(3);
    }

    @Test
    void givenNoActiveAdverts_whenGetActiveAdvertsPhoneNumbers_thenReturnEmptyString() {
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(1, Collections.singleton("+375291111111")));

        String phoneNumbersString = advertServiceFacade.getActiveAdvertsPhoneNumbersString(
                GetActiveAdvertsPhoneNumbersQuery.builder()
                        .lastModificationDate(LocalDate.MAX)
                        .lastAdvertisementDate(LocalDate.MIN)
                        .build());

        assertThat(phoneNumbersString).isEmpty();
    }

    @Test
    void givenNoValidPhoneNumbers_whenGetActiveAdvertsPhoneNumbers_thenReturnEmptyString() {
        advertServiceFacade.createOrUpdate(getSaveAdvertCommand(1, Collections.singleton("+375291111111")));

        advertServiceFacade.getActiveAdvertsPhoneNumbersString(getActiveAdvertsPhoneNumbersQuery());

        String phoneNumbersString = advertServiceFacade.getActiveAdvertsPhoneNumbersString(
                GetActiveAdvertsPhoneNumbersQuery.builder()
                        .lastModificationDate(LocalDate.MIN)
                        .lastAdvertisementDate(LocalDate.MIN)
                        .build());

        assertThat(phoneNumbersString).isEmpty();
    }

    private GetActiveAdvertsPhoneNumbersQuery getActiveAdvertsPhoneNumbersQuery() {
        return GetActiveAdvertsPhoneNumbersQuery.builder()
                .lastModificationDate(LocalDate.MIN)
                .lastAdvertisementDate(LocalDate.MIN)
                .build();
    }

    private SaveAdvertCommand getSaveAdvertCommand(int modifier, Set<String> phoneNumberStrings) {
        return SaveAdvertCommand.builder()
                .urlString("url")
                .city("" + modifier)
                .district("" + modifier)
                .street("" + modifier)
                .houseNumber(1)
                .level(1)
                .amount(new BigDecimal(0))
                .currencyString("BYN")
                .phoneNumberStrings(phoneNumberStrings)
                .build();
    }
}
