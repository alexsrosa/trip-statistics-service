package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.api.OutTopZonesDto;
import com.department.transportation.trip.statistics.api.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.enums.OrderTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:23
 */
@RequiredArgsConstructor
@Component
public class TopZonesUseCase {

    public OutTopZonesListDto fetchTopZoneByOrder(String order) {
        // TODO: Mock
        if (OrderTypeEnum.DROP_OFF.getValue().equals(order)) {
            return OutTopZonesListDto.builder()
                    .topZones(List.of(OutTopZonesDto.builder().zone("123 - dropoffs").dropOffTotal(12).pickupTotal(23).build()))
                    .build();
        }

        return OutTopZonesListDto.builder()
                .topZones(List.of(OutTopZonesDto.builder().zone("123 - pickups").dropOffTotal(12).pickupTotal(23).build()))
                .build();
    }
}
