package com.department.transportation.trip.statistics.core.exceptions;

import org.slf4j.Logger;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:13
 */
public class ZonesLoadException extends GeneralException {

    public ZonesLoadException() {
        super("Unable to load the zones to the system.!");
    }

    public ZonesLoadException(Exception e) {
        super("Unable to load the zones to the system! Details: " + e.getMessage());
    }

    public ZonesLoadException(String message) {
        super(message);
    }

    public ZonesLoadException(String message, Logger log) {
        super(message, log);
    }

    public ZonesLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZonesLoadException(String message, Throwable cause, Logger log) {
        super(message, cause, log);
    }
}
