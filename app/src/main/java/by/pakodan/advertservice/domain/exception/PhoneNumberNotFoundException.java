package by.pakodan.advertservice.domain.exception;

import by.pakodan.advertservice.domain.PhoneNumberId;

public class PhoneNumberNotFoundException extends RuntimeException {

    public PhoneNumberNotFoundException(PhoneNumberId id) {
        super(String.format("Phone number with id = %s not found", id.getValue()));
    }
}
