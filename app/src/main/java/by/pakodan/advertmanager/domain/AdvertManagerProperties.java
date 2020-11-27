package by.pakodan.advertmanager.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class AdvertManagerProperties {

    private int fetchingActiveAdvertsHoursInterval;
    private int advertisementIntervalInDays;
}
