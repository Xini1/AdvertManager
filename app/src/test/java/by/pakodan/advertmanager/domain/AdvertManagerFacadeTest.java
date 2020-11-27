package by.pakodan.advertmanager.domain;

import by.pakodan.advertmanager.domain.dto.SaveAdvertCommand;
import by.pakodan.advertmanager.domain.exception.AdvertNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdvertManagerFacadeTest {

    private AdvertManagerFacade advertManagerFacade;

    @BeforeEach
    void setUp() {
        advertManagerFacade = new AdvertManagerConfiguration().advertManagerFacadeForTests();
    }

    @Test
    void givenNewAdvert_whenCreateOrUpdate_thenAdvertCreated() {
        long id = advertManagerFacade.createOrUpdate(getSaveAdvertCommand());

        assertThat(advertManagerFacade.getById(id)).isNotNull();
    }

    @Test
    void givenExistedAdvert_whenCreateOrUpdate_thenAdvertUpdated() {
        SaveAdvertCommand saveAdvertCommand = getSaveAdvertCommand();
        long firstId = advertManagerFacade.createOrUpdate(saveAdvertCommand);

        saveAdvertCommand = saveAdvertCommand.toBuilder()
                .urlString("new url")
                .phoneNumberStrings(Set.of("+375291111111", "+375292222222"))
                .build();

        long secondId = advertManagerFacade.createOrUpdate(saveAdvertCommand);

        assertThat(advertManagerFacade.getById(secondId)).isNotNull();
        assertThat(secondId).isEqualTo(firstId);
    }

    @Test
    void givenSaveAdvertCommandInvalid_whenCreateOrUpdate_thenThrowConstraintViolationException() {
        SaveAdvertCommand invalidSaveAdvertCommand = getSaveAdvertCommand().toBuilder()
                .urlString(null)
                .build();

        assertThrows(ConstraintViolationException.class, () -> advertManagerFacade.createOrUpdate(invalidSaveAdvertCommand));
    }

    @Test
    void givenAdvertDoNotExist_whenGetById_thenThrowAdvertNotFoundException() {
        assertThrows(AdvertNotFoundException.class, () -> advertManagerFacade.getById(1));
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