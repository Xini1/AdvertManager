package by.pakodan.advertmanager.domain;

public class AdvertManagerConfiguration {

    public AdvertManagerFacade advertManagerFacadeForTests() {
        return advertManagerFacade(new InMemoryAdvertRepository(), new InMemoryPhoneNumberRepository());
    }

    AdvertManagerFacade advertManagerFacade(AdvertRepository advertRepository,
                                            PhoneNumberRepository phoneNumberRepository) {

        return AdvertManagerFacade.builder()
                .advertRepository(advertRepository)
                .phoneNumberRepository(phoneNumberRepository)
                .advertFactory(new AdvertFactory())
                .build();
    }
}
