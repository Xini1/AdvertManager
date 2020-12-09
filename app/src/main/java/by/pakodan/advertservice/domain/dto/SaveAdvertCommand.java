package by.pakodan.advertservice.domain.dto;

import lombok.Value;

import java.util.Set;

@Value
public class SaveAdvertCommand {

    String urlString;
    String city;
    String district;
    String street;
    int house;
    int level;
    int totalLevels;
    int rooms;
    int area;
    String description;
    String amountString;
    String currencyString;
    Set<String> phoneNumberStrings;
}
