package by.pakodan.advertservice.domain.exception;

import by.pakodan.advertservice.domain.AdvertId;

public class AdvertNotFoundException extends RuntimeException {

    public AdvertNotFoundException(AdvertId id) {
        super(String.format("Advert with id = %s not found", id.getValue()));
    }
}
