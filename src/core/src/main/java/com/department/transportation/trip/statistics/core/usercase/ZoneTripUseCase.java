package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:42
 */
@RequiredArgsConstructor
@Component
public class ZoneTripUseCase {
    public OutZoneTripDto fetchZoneTripsWithFilter(String zone, String date) {
        // TODO: Mock
        return OutZoneTripDto.builder().zone("123").date(LocalDate.now()).pickups(23).dropoffs(44).build();
    }
}
