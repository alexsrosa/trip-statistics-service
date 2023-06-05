package com.department.transportation.trip.statistics.controllers.docs;


import com.department.transportation.trip.statistics.api.InTopZonesQueryParam;
import com.department.transportation.trip.statistics.api.InZoneTripsQueryParam;
import com.department.transportation.trip.statistics.api.OutListYellowDto;
import com.department.transportation.trip.statistics.api.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:13
 */
@Tag(name = "Trip Statistics")
public interface TripStatisticsControllerDoc {

    @Operation(summary = "Returns top zones",
            responses = {
                    @ApiResponse(description = "Request processed correctly.", responseCode = "200"),
                    @ApiResponse(description = "The request is incomplete or invalid.", responseCode = "400")
            })
    @ApiResponsesWithOk
    ResponseEntity<OutTopZonesListDto> fetchTopZoneByOrder(InTopZonesQueryParam inTopZonesQueryParam);

    @Operation(summary = "Returns zone trips",
            responses = {
                    @ApiResponse(description = "Request processed correctly.", responseCode = "200"),
                    @ApiResponse(description = "The request is incomplete or invalid.", responseCode = "400")
            })
    @ApiResponsesWithOk
    ResponseEntity<OutZoneTripDto> fetchZoneTripsByZoneAndDate(InZoneTripsQueryParam inZoneTripsQueryParam);

    @Operation(summary = "Fetch Yellow Taxies",
            responses = {
                    @ApiResponse(description = "Request processed correctly.", responseCode = "200"),
                    @ApiResponse(description = "The request is incomplete or invalid.", responseCode = "400")
            })
    @ApiResponsesWithOk
    ResponseEntity<Page<OutListYellowDto>> fetchYellowByParam(@RequestParam(value = "id", required = false) String id,
                                                              @RequestParam(value = "pu_date", required = false) String pickupDatetime,
                                                              @RequestParam(value = "do_date", required = false) String dropOffDatetime,
                                                              @RequestParam(value = "pu_location", required = false) Long pickupLocation,
                                                              @RequestParam(value = "do_location", required = false) Long dropOffLocation,
                                                              Pageable pageable);
}