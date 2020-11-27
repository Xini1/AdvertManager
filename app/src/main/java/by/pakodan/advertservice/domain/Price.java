package by.pakodan.advertservice.domain;

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
