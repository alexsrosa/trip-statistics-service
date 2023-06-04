package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.api.OutListYellowDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:42
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FetchYellowUseCase {
    public List<OutListYellowDto> fetchYellowByParam(String pickupDatetime, String dropOffDatetime,
                                                     Integer pickupLocation, Integer dropOffLocation) {
        log.info("Received param: pickupDatetime={}, dropOffDatetime={}, pickupLocation={}, dropOffLocation={} ",
                pickupDatetime, dropOffDatetime, pickupLocation, dropOffLocation);

        // TODO: Mock code
        List<OutListYellowDto> listMocked = List.of(OutListYellowDto.builder().id(1L)
                        .pickupDatetime(LocalDateTime.now()).dropOffDatetime(LocalDateTime.now())
                        .pickupLocation(123).dropOffLocation(345).build(),
                OutListYellowDto.builder().id(1L)
                        .pickupDatetime(LocalDateTime.now()).dropOffDatetime(LocalDateTime.now())
                        .pickupLocation(123).dropOffLocation(345).build());

        return listMocked;
    }
}
