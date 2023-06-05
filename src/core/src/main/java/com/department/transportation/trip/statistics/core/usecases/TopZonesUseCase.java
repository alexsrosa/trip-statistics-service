package com.department.transportation.trip.statistics.core.usecases;

import com.department.transportation.trip.statistics.api.dtos.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.enums.OrderTypeEnum;
import com.department.transportation.trip.statistics.api.queryparams.InTopZonesQueryParam;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:23
 */
@RequiredArgsConstructor
@Component
public class TopZonesUseCase {

    public static final String CACHE_FETCH_TOP_ZONE_BY_ORDER = "CacheFetchTopZoneByOrder";

    private final TaxisService taxisService;
    private final CacheManager cacheManager;

    @Cacheable(value = CACHE_FETCH_TOP_ZONE_BY_ORDER, key = "#inTopZonesQueryParam.order")
    public OutTopZonesListDto fetchTopZoneByOrder(InTopZonesQueryParam inTopZonesQueryParam) {

        if (OrderTypeEnum.DROP_OFF.getValue().equals(inTopZonesQueryParam.getOrder())) {
            return OutTopZonesListDto.builder().topZones(taxisService.findTop5ZonesOrderByDropOff()).build();
        }

        return OutTopZonesListDto.builder().topZones(taxisService.findTop5ZonesOrderByPickups()).build();
    }

    public void evictCaches() {
        ofNullable(cacheManager.getCache(CACHE_FETCH_TOP_ZONE_BY_ORDER))
                .ifPresent(Cache::clear);

    }
}
