package com.department.transportation.trip.statistics.core.exceptions;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:13
 */
public class ZonesLoadException extends GeneralException {

    public ZonesLoadException(Exception e) {
        super("Unable to load the zones to the system! Details: " + e.getMessage());
    }
}
