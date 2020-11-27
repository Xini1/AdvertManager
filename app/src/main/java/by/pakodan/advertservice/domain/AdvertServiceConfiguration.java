package by.pakodan.advertservice.domain;

import java.time.Clock;

public class AdvertServiceConfiguration {

    AdvertServiceFacade advertServiceFacadeForTests(Clock clock) {
        InMemoryPhoneNumberRepository inMemoryPhoneNumberRepository = new InMemoryPhoneNumberRepository();

        return advertServiceFacade(new InMemoryAdvertRepository(inMemoryPhoneNumberRepository),
                inMemoryPhoneNumberRepository, clock);
    }

    AdvertServiceFacade advertServiceFacade(AdvertRepository advertRepository,
                                            PhoneNumberRepository phoneNumberRepository,
                                            Clock clock) {

        return new AdvertServiceFacade(advertRepository, phoneNumberRepository, new AdvertFactory(clock), clock);
    }
}
