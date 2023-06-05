package com.department.transportation.trip.statistics.core.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 15:58
 */
@UtilityClass
public class LocalDateTimeUtils {

    public final DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DATA_FORMAT, ISO_LOCAL_DATE_TIME, ISO_OFFSET_DATE_TIME);

    public LocalDateTime convertFromString(String date) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (Exception ignored) {
                // Ignored
            }
        }

        return null;
    }
}
