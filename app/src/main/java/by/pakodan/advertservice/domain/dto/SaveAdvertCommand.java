package by.pakodan.advertservice.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@ToString
public class SaveAdvertCommand {

    @NotBlank
    private final String urlString;
    @NotBlank
    private final String city;
    @NotBlank
    private final String district;
    @NotBlank
    private final String street;
    @Min(1)
    private final int houseNumber;
    @Min(1)
    private final int level;
    private final String description;
    @NotNull
    private final BigDecimal amount;
    @NotBlank
    private final String currencyString;
    @NotNull
    @Size(min = 1)
    private final Set<String> phoneNumberStrings;
}
