package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.api.InZoneTripsQueryParam;
import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import com.department.transportation.trip.statistics.core.exceptions.BadRequestException;
import com.department.transportation.trip.statistics.core.exceptions.ZoneNotFoundException;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import com.department.transportation.trip.statistics.core.services.ZoneService;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:42
 */
@RequiredArgsConstructor
@Component
public class ZoneTripUseCase {

    private final ZoneService zoneService;
    private final TaxisService taxisService;

    @Cacheable(value = "CacheFetchZoneTripsWithFilter",
            key = "T(java.util.Objects).hash(#inZoneTripsQueryParam.zone, #inZoneTripsQueryParam.date)")
    public OutZoneTripDto fetchZoneTripsWithFilter(InZoneTripsQueryParam inZoneTripsQueryParam) {

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
}
