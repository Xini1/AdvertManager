package by.pakodan.advertservice.domain.dto;

import lombok.Value;

@Value
public class AdvertDto {

    Long id;
    String url;
    String addressString;
    String price;
    String phoneNumbersString;
}
