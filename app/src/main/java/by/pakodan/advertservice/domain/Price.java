package by.pakodan.advertservice.domain;

import by.pakodan.advertservice.utils.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Price {

    private final Currency currency;
    private final BigDecimal amount;

    static Price of(Currency currency, String amount) {
        Preconditions.checkNotNull(currency, "currency");
        Preconditions.checkNotBlank(amount, "amount");
        Preconditions.checkMatchRegex(amount, "\\d+(\\.\\d{1,2})?", "amount");

        return new Price(currency, new BigDecimal(amount));
    }
}
