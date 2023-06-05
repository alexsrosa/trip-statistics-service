package com.department.transportation.trip.statistics.model.converters;


import com.department.transportation.trip.statistics.api.OutListYellowDto;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 00:43
 */
class TaxisConverterTest {

    @Test
    void Given_ConvertDBOToDTO_When_ReceiveObject_Then_ConvertCorrectly() {
        TaxisEntity taxisEntity = TaxisEntity.builder()
                .id(UUID.randomUUID())
                .pickupDatetime(LocalDateTime.now())
                .dropOffDatetime(LocalDateTime.now())
                .pickupLocation(ZoneEntity.builder().id(123L).build())
                .dropOffLocation(ZoneEntity.builder().id(124L).build())
                .build();

        OutListYellowDto dto = TaxisConverter.convertDBOToDTO.apply(taxisEntity);

        assertNotNull(dto);
        assertEquals(taxisEntity.getId(), dto.getId());
        assertEquals(taxisEntity.getPickupLocation().getId(), dto.getPickupLocation());
        assertEquals(taxisEntity.getDropOffLocation().getId(), dto.getDropOffLocation());
        assertEquals(taxisEntity.getPickupDatetime(), dto.getPickupDatetime());
        assertEquals(taxisEntity.getDropOffDatetime(), dto.getDropOffDatetime());
    }

    @Test
    void Given_ConvertDBOToDTO_When_ReceiveNull_Then_ConvertNotWork() {
        OutListYellowDto dto = TaxisConverter.convertDBOToDTO.apply(null);

        assertNull(dto);
    }
}