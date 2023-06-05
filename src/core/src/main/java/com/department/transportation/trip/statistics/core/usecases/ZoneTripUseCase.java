package com.department.transportation.trip.statistics.core.usecases;

import com.department.transportation.trip.statistics.api.dtos.OutZoneTripDto;
import com.department.transportation.trip.statistics.api.queryparams.InZoneTripsQueryParam;
import com.department.transportation.trip.statistics.core.exceptions.BadRequestException;
import com.department.transportation.trip.statistics.core.exceptions.ZoneNotFoundException;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import com.department.transportation.trip.statistics.core.services.ZoneService;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:42
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ZoneTripUseCase {

    public static final String CACHE_FETCH_ZONE_TRIPS_WITH_FILTER = "CacheFetchZoneTripsWithFilter";

    private final ZoneService zoneService;
    private final TaxisService taxisService;
    private final CacheManager cacheManager;

    @Cacheable(value = CACHE_FETCH_ZONE_TRIPS_WITH_FILTER,
            key = "T(java.util.Objects).hash(#inZoneTripsQueryParam.zone, #inZoneTripsQueryParam.date)")
    public OutZoneTripDto fetchZoneTripsWithFilter(InZoneTripsQueryParam inZoneTripsQueryParam) {

        log.info("Received param: {}", inZoneTripsQueryParam.toString());

        ZoneEntity zoneEntity = zoneService.findOneByIdOrZone(inZoneTripsQueryParam.getZone())
                .orElseThrow(ZoneNotFoundException::new);

        OutZoneTripDto outZoneTripDto = taxisService.fetchZoneTripsSumsByZoneIdAndDate(zoneEntity.getId(), inZoneTripsQueryParam.getDate());
        if (isNull(outZoneTripDto)) {
            throw new BadRequestException("Bad Request");
        }

        outZoneTripDto.setZone(zoneEntity.getZone());
        outZoneTripDto.setDate(inZoneTripsQueryParam.getDate());

        return outZoneTripDto;
    }

    public void evictCaches() {
        ofNullable(cacheManager.getCache(CACHE_FETCH_ZONE_TRIPS_WITH_FILTER))
                .ifPresent(Cache::clear);

    }
}
