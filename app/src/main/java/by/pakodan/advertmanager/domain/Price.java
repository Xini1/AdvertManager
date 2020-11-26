package by.pakodan.advertmanager.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
class Price {

    BigDecimal amount;
    Currency currency;
}
