package com.department.transportation.trip.statistics.core.mappers;

import com.department.transportation.trip.statistics.core.utils.LocalDateTimeUtils;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import com.department.transportation.trip.statistics.model.enums.TaxisTypeEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 15:52
 */
@UtilityClass
@Slf4j
public class TaxisMapper {

    public final Function<List<String[]>, List<TaxisEntity>> mapGreenToDbo = columns ->
            columns.stream()
                    .map(column -> TaxisEntity.builder()
                            .pickupDatetime(LocalDateTime.parse(column[2], LocalDateTimeUtils.DATA_FORMAT))
                            .dropOffDatetime(LocalDateTime.parse(column[3], LocalDateTimeUtils.DATA_FORMAT))
                            .pickupLocation(ZoneEntity.builder().id(Long.valueOf(column[6])).build())
                            .dropOffLocation(ZoneEntity.builder().id(Long.valueOf(column[7])).build())
                            .taxisType(TaxisTypeEnum.GREEN)
                            .build())
                    .toList();

    public final Function<List<String[]>, List<TaxisEntity>> mapYellowToDbo = columns ->
            columns.stream()
                    .map(column -> TaxisEntity.builder()
                            .pickupDatetime(LocalDateTime.parse(column[2], LocalDateTimeUtils.DATA_FORMAT))
                            .dropOffDatetime(LocalDateTime.parse(column[3], LocalDateTimeUtils.DATA_FORMAT))
                            .pickupLocation(ZoneEntity.builder().id(Long.valueOf(column[8])).build())
                            .dropOffLocation(ZoneEntity.builder().id(Long.valueOf(column[9])).build())
                            .taxisType(TaxisTypeEnum.YELLOW)
                            .build())
                    .toList();
}
