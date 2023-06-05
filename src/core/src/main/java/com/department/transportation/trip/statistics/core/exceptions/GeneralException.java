package com.department.transportation.trip.statistics.core.exceptions;

import org.slf4j.Logger;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:13
 */
public class GeneralException extends RuntimeException {

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Logger log) {
        super(message);
        log.error(message);
    }
}
