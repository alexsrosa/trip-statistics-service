package com.department.transportation.trip.statistics.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 12:07
 */
@AllArgsConstructor
@Getter
public enum ServiceZoneEnum {

    AIRPORTS("Airports"),
    EWR("EWR"),
    BORO_ZONE("Boro Zone"),
    YELLOW_ZONE("Yellow Zone"),
    NA("N/A");

    private final String value;

    public static ServiceZoneEnum getByValue(String value) {
        return Arrays.stream(ServiceZoneEnum.values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElse(ServiceZoneEnum.NA);
    }

}
