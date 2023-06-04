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
public class NotFoundException extends GeneralException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Logger log) {
        super(message, log);
    }

    public NotFoundException(String message, String detail) {
        super(String.format(message, detail));
    }
}