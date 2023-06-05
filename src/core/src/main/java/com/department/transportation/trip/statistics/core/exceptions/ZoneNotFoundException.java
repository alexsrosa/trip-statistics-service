package com.department.transportation.trip.statistics.core.exceptions;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 21:49
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ZoneNotFoundException extends NotFoundException {

    public ZoneNotFoundException() {
        super("Zone Not Found");
    }

    public ZoneNotFoundException(String message) {
        super(message);
    }

    public ZoneNotFoundException(String message, Logger log) {
        super(message, log);
    }

    public ZoneNotFoundException(String message, String detail) {
        super(String.format(message, detail));
    }
}