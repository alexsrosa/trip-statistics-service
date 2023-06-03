package com.department.transportation.trip.statistics.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 12:07
 */
@AllArgsConstructor
@Getter
public enum ServiceZoneEnum {

    EWR("EWR"),
    BORO_ZONE("Boro Zone"),
    YELLOW_ZONE("Yellow Zone");

    private final String value;

}
