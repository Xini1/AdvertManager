package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.domain.dto.SaveAdvertCommand;
import by.pakodan.advertservice.domain.exception.AdvertNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateOrUpdateAdvertTest {

    private AdvertServiceFacade advertServiceFacade;

    @BeforeEach
    void setUp() {
        advertServiceFacade = new AdvertServiceConfiguration().advertServiceFacadeForTests(Clock.systemDefaultZone());
    }

    @Test
    void givenNewAdvert_whenCreateOrUpdate_thenAdvertCreated() {
        long id = advertServiceFacade.createOrUpdate(getSaveAdvertCommand());

        assertThat(advertServiceFacade.getById(id)).isNotNull();
    }

    @Test
    void givenExistedAdvert_whenCreateOrUpdate_thenAdvertUpdated() {
        SaveAdvertCommand saveAdvertCommand = getSaveAdvertCommand();
        long firstId = advertServiceFacade.createOrUpdate(saveAdvertCommand);

        saveAdvertCommand = saveAdvertCommand.toBuilder()
                .urlString("new url")
                .phoneNumberStrings(Set.of("+375291111111", "+375292222222"))
                .build();

        long secondId = advertServiceFacade.createOrUpdate(saveAdvertCommand);

        assertThat(advertServiceFacade.getById(secondId)).isNotNull();
        assertThat(secondId).isEqualTo(firstId);
    }

    @Test
    void givenSaveAdvertCommandInvalid_whenCreateOrUpdate_thenThrowConstraintViolationException() {
        SaveAdvertCommand invalidSaveAdvertCommand = getSaveAdvertCommand().toBuilder()
                .urlString(null)
                .build();

        assertThrows(ConstraintViolationException.class, () -> advertServiceFacade.createOrUpdate(invalidSaveAdvertCommand));
    }

    @Test
    void givenAdvertDoNotExist_whenGetById_thenThrowAdvertNotFoundException() {
        assertThrows(AdvertNotFoundException.class, () -> advertServiceFacade.getById(1));
    }

    private SaveAdvertCommand getSaveAdvertCommand() {
        return SaveAdvertCommand.builder()
                .urlString("url")
                .city("city")
                .district("district")
                .street("street")
                .houseNumber(1)
                .level(1)
                .amount(new BigDecimal(0))
                .currencyString("BYN")
                .phoneNumberStrings(Collections.singleton("+375291111111"))
                .build();
    }
}