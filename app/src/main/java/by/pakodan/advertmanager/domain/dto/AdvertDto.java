package by.pakodan.advertmanager.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Getter
public class AdvertDto {

    private final Long id;
    private final String url;
    private final String city;
    private final String street;
    private final int houseNumber;
    private final int level;
    private final BigDecimal amount;
    private final String currency;
    private final Set<String> phoneNumbers;
}
