package by.pakodan.advertservice.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@ToString
public class GetActiveAdvertsPhoneNumbersQuery {

    private final LocalDate lastModificationDate;
    private final LocalDate lastAdvertisementDate;
}
