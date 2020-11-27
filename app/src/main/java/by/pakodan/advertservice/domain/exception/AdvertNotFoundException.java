package by.pakodan.advertservice.domain.exception;

public class AdvertNotFoundException extends RuntimeException {

    public AdvertNotFoundException(Long id) {
        super(String.format("Advert with id = %d not found", id));
    }
}
