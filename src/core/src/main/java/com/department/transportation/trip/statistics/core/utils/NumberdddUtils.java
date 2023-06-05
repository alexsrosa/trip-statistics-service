package com.department.transportation.trip.statistics.core.utils;

import lombok.experimental.UtilityClass;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 04:00
 */
@UtilityClass
public class NumberdddUtils {

    public static boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
