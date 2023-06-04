package com.department.transportation.trip.statistics.controllers.docs;


import com.department.transportation.trip.statistics.api.OutListYellowDto;
import com.department.transportation.trip.statistics.api.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:13
 */
@Tag(name = "Trip Statistics")
public interface TripStatisticsControllerDoc {

    @Operation(summary = "It records the opening hours of stores, on an exceptional basis.",
            security = {
                    @SecurityRequirement(name = "oAuth2", scopes = {"ROLE_ADMIN"}),
                    @SecurityRequirement(name = "bearerAuth", scopes = {"ROLE_ADMIN"})
            }, responses = {
            @ApiResponse(description = "Request processed correctly.", responseCode = "200"),
            @ApiResponse(description = "The request is incomplete or invalid.", responseCode = "400"),
            @ApiResponse(description = "When there is a temporal overlap of schedule interval.", responseCode = "409")
    })
    @ApiResponsesWithOk
    ResponseEntity<OutTopZonesListDto> fetchTopZoneByOrder(@RequestParam("order") String order);

    @Operation(summary = "It records the opening hours of stores, on an exceptional basis.",
            security = {
                    @SecurityRequirement(name = "oAuth2", scopes = {"ROLE_ADMIN"}),
                    @SecurityRequirement(name = "bearerAuth", scopes = {"ROLE_ADMIN"})
            }, responses = {
            @ApiResponse(description = "Request processed correctly.", responseCode = "200"),
            @ApiResponse(description = "The request is incomplete or invalid.", responseCode = "400"),
            @ApiResponse(description = "When there is a temporal overlap of schedule interval.", responseCode = "409")
    })
    @ApiResponsesWithOk
    ResponseEntity<OutZoneTripDto> fetchZoneTripsByZoneAndDate(@RequestParam(required = false) String zone,
                                                               @RequestParam(required = false) String date);

    @Operation(summary = "It records the opening hours of stores, on an exceptional basis.",
            security = {
                    @SecurityRequirement(name = "oAuth2", scopes = {"ROLE_ADMIN"}),
                    @SecurityRequirement(name = "bearerAuth", scopes = {"ROLE_ADMIN"})
            }, responses = {
            @ApiResponse(description = "Request processed correctly.", responseCode = "200"),
            @ApiResponse(description = "The request is incomplete or invalid.", responseCode = "400"),
            @ApiResponse(description = "When there is a temporal overlap of schedule interval.", responseCode = "409")
    })
    @ApiResponsesWithOk
    ResponseEntity<List<OutListYellowDto>> fetchYellowByParam(@RequestParam(value = "pu_date", required = false) String pickupDatetime,
                                                              @RequestParam(value = "do_date", required = false) String dropOffDatetime,
                                                              @RequestParam(value = "pu_location", required = false) Integer pickupLocation,
                                                              @RequestParam(value = "do_location", required = false) Integer dropOffLocation);
}