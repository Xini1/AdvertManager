package by.pakodan.advertmanager.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
public class SaveAdvertCommand {

    private final String urlString;
    private final String city;
    private final String district;
    private final String street;
    private final int houseNumber;
    private final int level;
    private final String description;
    private final BigDecimal amount;
    private final String currencyString;
    private final Set<String> phoneNumberStrings;
}
