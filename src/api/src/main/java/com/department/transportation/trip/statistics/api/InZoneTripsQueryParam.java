package com.department.transportation.trip.statistics.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 03:18
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InZoneTripsQueryParam {

    @NotBlank
    private String zone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;
}
