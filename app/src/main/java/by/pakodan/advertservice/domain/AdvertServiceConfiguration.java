package by.pakodan.advertservice.domain;

public class AdvertServiceConfiguration {

    AdvertServiceFacade advertServiceFacadeForTests() {
        return advertServiceFacade(new InMemoryAdvertRepository(), new InMemoryPhoneNumberRepository());
    }

    AdvertServiceFacade advertServiceFacade(AdvertRepository advertRepository,
                                            PhoneNumberRepository phoneNumberRepository) {

        return new AdvertServiceFacade(advertRepository, phoneNumberRepository, new AdvertFactory());
    }
}
