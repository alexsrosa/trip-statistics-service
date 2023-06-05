package com.department.transportation.trip.statistics.controllers;


import com.department.transportation.trip.statistics.api.dtos.OutListYellowDto;
import com.department.transportation.trip.statistics.api.dtos.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.dtos.OutZoneTripDto;
import com.department.transportation.trip.statistics.api.queryparams.InTopZonesQueryParam;
import com.department.transportation.trip.statistics.api.queryparams.InZoneTripsQueryParam;
import com.department.transportation.trip.statistics.controllers.docs.TripStatisticsControllerDoc;
import com.department.transportation.trip.statistics.core.usecases.FetchYellowUseCase;
import com.department.transportation.trip.statistics.core.usecases.TopZonesUseCase;
import com.department.transportation.trip.statistics.core.usecases.ZoneTripUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:13
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/v1/trips")
public class TripStatisticsController implements TripStatisticsControllerDoc {

    private final TopZonesUseCase topZonesUseCase;
    private final ZoneTripUseCase zoneTripUseCase;
    private final FetchYellowUseCase fetchYellowUseCase;

    @GetMapping("/top_zones")
    public ResponseEntity<OutTopZonesListDto> fetchTopZoneByOrder(@Valid InTopZonesQueryParam inTopZonesQueryParam) {
        return ResponseEntity.ok(topZonesUseCase.fetchTopZoneByOrder(inTopZonesQueryParam));
    }

    @GetMapping("/zone-trips")
    public ResponseEntity<OutZoneTripDto> fetchZoneTripsByZoneAndDate(@Valid InZoneTripsQueryParam inZoneTripsQueryParam) {
        return ResponseEntity.ok(zoneTripUseCase.fetchZoneTripsWithFilter(inZoneTripsQueryParam));
    }

    @GetMapping("/list-yellow")
    public ResponseEntity<Page<OutListYellowDto>> fetchYellowByParam(@RequestParam(value = "id", required = false) String id,
                                                                     @RequestParam(value = "pu_date", required = false) String pickupDatetime,
                                                                     @RequestParam(value = "do_date", required = false) String dropOffDatetime,
                                                                     @RequestParam(value = "pu_location", required = false) Long pickupLocation,
                                                                     @RequestParam(value = "do_location", required = false) Long dropOffLocation,
                                                                     Pageable pageable) {
        return ResponseEntity.ok(
                fetchYellowUseCase.fetchYellowByParam(id, pickupDatetime, dropOffDatetime, pickupLocation, dropOffLocation, pageable));
    }
}
