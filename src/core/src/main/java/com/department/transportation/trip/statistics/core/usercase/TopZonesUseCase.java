package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.api.InTopZonesQueryParam;
import com.department.transportation.trip.statistics.api.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.enums.OrderTypeEnum;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:23
 */
@RequiredArgsConstructor
@Component
public class TopZonesUseCase {

    private final TaxisService taxisService;

    @Cacheable(value = "CacheFetchTopZoneByOrder", key = "#inTopZonesQueryParam.order")
    public OutTopZonesListDto fetchTopZoneByOrder(InTopZonesQueryParam inTopZonesQueryParam) {

        if (OrderTypeEnum.DROP_OFF.getValue().equals(inTopZonesQueryParam.getOrder())) {
            return OutTopZonesListDto.builder().topZones(taxisService.findTop5ZonesOrderByDropOff()).build();
        }

        return OutTopZonesListDto.builder().topZones(taxisService.findTop5ZonesOrderByPickups()).build();
    }
}
