package com.department.transportation.trip.statistics.controllers;


import com.department.transportation.trip.statistics.api.OutListYellowDto;
import com.department.transportation.trip.statistics.api.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import com.department.transportation.trip.statistics.controllers.docs.TripStatisticsControllerDoc;
import com.department.transportation.trip.statistics.core.usercase.FetchYellowUseCase;
import com.department.transportation.trip.statistics.core.usercase.TopZonesUseCase;
import com.department.transportation.trip.statistics.core.usercase.ZoneTripUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:13
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/v1/trip")
public class TripStatisticsController implements TripStatisticsControllerDoc {

    private final TopZonesUseCase topZonesUseCase;
    private final ZoneTripUseCase zoneTripUseCase;
    private final FetchYellowUseCase fetchYellowUseCase;

    @GetMapping("/top_zones")
    public ResponseEntity<OutTopZonesListDto> fetchTopZoneByOrder(@RequestParam("order") String order) {
        return ResponseEntity.ok(topZonesUseCase.fetchTopZoneByOrder(order));
    }

    @GetMapping("/zone-trips")
    public ResponseEntity<OutZoneTripDto> fetchZoneTripsByZoneAndDate(@RequestParam(required = false) String zone,
                                                                      @RequestParam(required = false) String date) {
        return ResponseEntity.ok(zoneTripUseCase.fetchZoneTripsWithFilter(zone, date));
    }

    @GetMapping("/list-yellow")
    public ResponseEntity<List<OutListYellowDto>> fetchYellowByParam(@RequestParam(value = "pu_date", required = false) String pickupDatetime,
                                                                     @RequestParam(value = "do_date", required = false) String dropOffDatetime,
                                                                     @RequestParam(value = "pu_location", required = false) Integer pickupLocation,
                                                                     @RequestParam(value = "do_location", required = false) Integer dropOffLocation) {
        return ResponseEntity.ok(fetchYellowUseCase.fetchYellowByParam(pickupDatetime, dropOffDatetime, pickupLocation, dropOffLocation));
    }
}
