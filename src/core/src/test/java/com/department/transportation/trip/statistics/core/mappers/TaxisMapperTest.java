package com.department.transportation.trip.statistics.core.mappers;

import com.department.transportation.trip.statistics.core.utils.LocalDateTimeUtils;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 19:47
 */
class TaxisMapperTest {

    @Test
    void Given_MapGreenToDbo_When_SingleColumn_Then_MapCorrectly() {
        Function<List<String[]>, List<TaxisEntity>> mapper = TaxisMapper.mapGreenToDbo;

        List<String[]> input = new ArrayList<>();
        input.add(new String[]{"1", "Green", "2023-06-04 10:00:00", "2023-06-04 11:00:00", "123", "456", "789", "999"});

        List<TaxisEntity> result = mapper.apply(input);

        assertEquals(1, result.size());

        TaxisEntity entity = result.get(0);
        assertEquals(LocalDateTime.parse("2023-06-04 10:00:00", LocalDateTimeUtils.DATA_FORMAT), entity.getPickupDatetime());
        assertEquals(LocalDateTime.parse("2023-06-04 11:00:00", LocalDateTimeUtils.DATA_FORMAT), entity.getDropOffDatetime());
        assertEquals(789L, entity.getPickupLocation().getId());
        assertEquals(999L, entity.getDropOffLocation().getId());
    }

    @Test
    void Given_MapGreenToDbo_When_MultipleColumns_Then_MapCorrectly() {
        Function<List<String[]>, List<TaxisEntity>> mapper = TaxisMapper.mapGreenToDbo;
        List<String[]> input = List.of(
                new String[]{"1", "Green", "2023-06-04 10:00:00", "2023-06-04 11:00:00", "123", "456", "789", "999"},
                new String[]{"2", "Green", "2023-06-04 12:00:00", "2023-06-04 13:00:00", "111", "222", "333", "444"}
        );

        // When
        List<TaxisEntity> result = mapper.apply(input);

        // Then
        assertEquals(2, result.size());

        TaxisEntity entity1 = result.get(0);
        assertEquals(LocalDateTime.parse("2023-06-04 10:00:00", LocalDateTimeUtils.DATA_FORMAT), entity1.getPickupDatetime());
        assertEquals(LocalDateTime.parse("2023-06-04 11:00:00", LocalDateTimeUtils.DATA_FORMAT), entity1.getDropOffDatetime());
        assertEquals(789L, entity1.getPickupLocation().getId());
        assertEquals(999L, entity1.getDropOffLocation().getId());

        TaxisEntity entity2 = result.get(1);
        assertEquals(LocalDateTime.parse("2023-06-04 12:00:00", LocalDateTimeUtils.DATA_FORMAT), entity2.getPickupDatetime());
        assertEquals(LocalDateTime.parse("2023-06-04 13:00:00", LocalDateTimeUtils.DATA_FORMAT), entity2.getDropOffDatetime());
        assertEquals(333L, entity2.getPickupLocation().getId());
        assertEquals(444L, entity2.getDropOffLocation().getId());
    }

    @Test
    void Given_MapYellowToDbo_When_SingleColumn_Then_MapCorrectly() {
        Function<List<String[]>, List<TaxisEntity>> mapper = TaxisMapper.mapYellowToDbo;

        List<String[]> input = new ArrayList<>();
        input.add(new String[]{"1", "Yellow", "2023-06-04 10:00:00", "2023-06-04 11:00:00", "123", "456", "789", "999", "555", "666"});

        List<TaxisEntity> result = mapper.apply(input);
        assertEquals(1, result.size());

        TaxisEntity entity = result.get(0);
        assertEquals(LocalDateTime.parse("2023-06-04 10:00:00", LocalDateTimeUtils.DATA_FORMAT), entity.getPickupDatetime());
        assertEquals(LocalDateTime.parse("2023-06-04 11:00:00", LocalDateTimeUtils.DATA_FORMAT), entity.getDropOffDatetime());
        assertEquals(555L, entity.getPickupLocation().getId());
        assertEquals(666L, entity.getDropOffLocation().getId());
    }

    @Test
    void Given_MapYellowToDbo_When_MultipleColumns_Then_MapCorrectly() {
        Function<List<String[]>, List<TaxisEntity>> mapper = TaxisMapper.mapYellowToDbo;
        List<String[]> input = List.of(
                new String[]{"1", "Yellow", "2023-06-04 10:00:00", "2023-06-04 11:00:00", "123", "456", "789", "999", "555", "666"},
                new String[]{"2", "Yellow", "2023-06-04 12:00:00", "2023-06-04 13:00:00", "111", "222", "333", "444", "777", "888"}
        );

        List<TaxisEntity> result = mapper.apply(input);

        assertEquals(2, result.size());

        TaxisEntity entity1 = result.get(0);
        assertEquals(LocalDateTime.parse("2023-06-04 10:00:00", LocalDateTimeUtils.DATA_FORMAT), entity1.getPickupDatetime());
        assertEquals(LocalDateTime.parse("2023-06-04 11:00:00", LocalDateTimeUtils.DATA_FORMAT), entity1.getDropOffDatetime());
        assertEquals(555L, entity1.getPickupLocation().getId());
        assertEquals(666L, entity1.getDropOffLocation().getId());

        TaxisEntity entity2 = result.get(1);
        assertEquals(LocalDateTime.parse("2023-06-04 12:00:00", LocalDateTimeUtils.DATA_FORMAT), entity2.getPickupDatetime());
        assertEquals(LocalDateTime.parse("2023-06-04 13:00:00", LocalDateTimeUtils.DATA_FORMAT), entity2.getDropOffDatetime());
        assertEquals(777L, entity2.getPickupLocation().getId());
        assertEquals(888L, entity2.getDropOffLocation().getId());
    }

}