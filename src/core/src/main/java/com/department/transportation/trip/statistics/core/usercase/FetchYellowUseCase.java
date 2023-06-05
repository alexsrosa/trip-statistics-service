package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.api.dtos.OutListYellowDto;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import com.department.transportation.trip.statistics.core.utils.LocalDateTimeUtils;
import com.department.transportation.trip.statistics.model.converters.TaxisConverter;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Objects.nonNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 20:42
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FetchYellowUseCase {

    private final TaxisService taxisService;

    public Page<OutListYellowDto> fetchYellowByParam(String id, String pickupDatetime, String dropOffDatetime,
                                                     Long pickupLocation, Long dropOffLocation,
                                                     Pageable pageable) {

        log.info("Received param: pickupDatetime={}, dropOffDatetime={}, pickupLocation={}, dropOffLocation={} ",
                pickupDatetime, dropOffDatetime, pickupLocation, dropOffLocation);


        TaxisEntity taxisEntity = TaxisEntity.builder()
                .id(nonNull(id) ? UUID.fromString(id) : null)
                .pickupDatetime(LocalDateTimeUtils.convertFromString(pickupDatetime))
                .dropOffDatetime(LocalDateTimeUtils.convertFromString(dropOffDatetime))
                .pickupLocation(nonNull(pickupLocation) ? ZoneEntity.builder().id(pickupLocation).build() : null)
                .dropOffLocation(nonNull(dropOffLocation) ? ZoneEntity.builder().id(dropOffLocation).build() : null)
                .build();


        Page<TaxisEntity> taxisEntities = taxisService.fetchTaxisByFilterAndPageable(Example.of(taxisEntity), pageable);
        return taxisEntities.isEmpty() ? Page.empty() : taxisEntities.map(TaxisConverter.convertDBOToDTO);
    }
}
