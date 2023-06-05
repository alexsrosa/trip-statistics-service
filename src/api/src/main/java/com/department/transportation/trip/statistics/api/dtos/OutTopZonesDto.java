package com.department.transportation.trip.statistics.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:16
 */
@ToString
@EqualsAndHashCode
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutTopZonesDto {

    private String zone;

    @JsonProperty("pu_total")
    private Long pickupTotal;

    @JsonProperty("do_total")
    private Long dropOffTotal;
}
