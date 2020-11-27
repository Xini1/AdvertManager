package by.pakodan.advertmanager.domain;

public class AdvertManagerConfiguration {

    public AdvertManagerFacade advertManagerFacadeForTests(AdvertManagerProperties advertManagerProperties) {
        return advertManagerFacade(new InMemoryAdvertRepository(), new InMemoryPhoneNumberRepository(),
                advertManagerProperties);
    }

    AdvertManagerFacade advertManagerFacade(AdvertRepository advertRepository,
                                            PhoneNumberRepository phoneNumberRepository,
                                            AdvertManagerProperties advertManagerProperties) {

        return AdvertManagerFacade.builder()
                .advertRepository(advertRepository)
                .phoneNumberRepository(phoneNumberRepository)
                .advertFactory(new AdvertFactory())
                .advertManagerProperties(advertManagerProperties)
                .build();
    }
}
