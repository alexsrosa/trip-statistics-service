package com.department.transportation.trip.statistics.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:35
 */
@AllArgsConstructor
public enum OrderTypeEnum {

    DROP_OFF("dropoffs"),
    PICK_UPS("pickups");

    @Getter
    private final String value;
}
