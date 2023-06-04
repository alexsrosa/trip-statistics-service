package com.department.transportation.trip.statistics.core.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 21:43
 */
@Getter
@Setter
@NoArgsConstructor
public class ExceptionListResponse {

    private List<String> messages;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime dateTime = LocalDateTime.now();

    public ExceptionListResponse(List<String> messages) {
        this.messages = messages;
    }
}
