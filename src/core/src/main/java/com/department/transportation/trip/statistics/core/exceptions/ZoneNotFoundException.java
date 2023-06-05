package com.department.transportation.trip.statistics.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 21:49
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ZoneNotFoundException extends GeneralException {

    public ZoneNotFoundException() {
        super("Zone Not Found");
    }
}