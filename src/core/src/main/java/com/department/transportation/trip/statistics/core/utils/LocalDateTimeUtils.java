package com.department.transportation.trip.statistics.core.utils;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 15:58
 */
@UtilityClass
public class LocalDateTimeUtils {

    public final DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
