package com.department.transportation.trip.statistics.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 03:18
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InTopZonesQueryParam {

    @NotBlank
    @Pattern(regexp = "(dropoffs|pickups)")
    private String order;
}
