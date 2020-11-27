package by.pakodan.advertservice.domain.exception;

public class PhoneNumberNotFoundException extends RuntimeException {

    public PhoneNumberNotFoundException(Long id) {
        super(String.format("Phone number with id = %d not found", id));
    }
}
