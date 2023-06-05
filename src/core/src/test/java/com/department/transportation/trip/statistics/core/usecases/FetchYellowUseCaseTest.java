package com.department.transportation.trip.statistics.core.usecases;

import com.department.transportation.trip.statistics.api.dtos.OutListYellowDto;
import com.department.transportation.trip.statistics.core.config.TestConfiguration;
import com.department.transportation.trip.statistics.core.utils.LocalDateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 18:13
 */
@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockServletContext.class, TestConfiguration.class})
@Sql({"/sql/import_clean_all_data.sql"})
class FetchYellowUseCaseTest {

    @Autowired
    private FetchYellowUseCase fetchYellowUseCase;

    @Autowired
    private ImportZonesUseCase importZonesUseCase;

    @Autowired
    private ImportTaxisUseCase importTaxisUseCase;

    @BeforeEach
    void init() {
        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();
    }

    @Test
    void Given_FetchYellowByParam_When_ExistsYellowTaxis_Then_ReturnsYellowTaxis() {
        String pickupDatetime = "2023-01-01T00:25:04";
        String dropOffDatetime = "2023-01-01T00:37:49";
        Long pickupLocation = 48L;
        Long dropOffLocation = 238L;

        Page<OutListYellowDto> outListYellowDtoPage = fetchYellowUseCase.fetchYellowByParam(null, pickupDatetime, dropOffDatetime, pickupLocation, dropOffLocation, Pageable.unpaged());

        assertEquals(1, outListYellowDtoPage.getTotalElements());

        OutListYellowDto outListYellowDto = outListYellowDtoPage.stream().findFirst().orElse(null);
        assertNotNull(outListYellowDto);
        assertEquals(LocalDateTimeUtils.convertFromString(pickupDatetime), outListYellowDto.getPickupDatetime());
        assertEquals(LocalDateTimeUtils.convertFromString(dropOffDatetime), outListYellowDto.getDropOffDatetime());
        assertEquals(pickupLocation, outListYellowDto.getPickupLocation());
        assertEquals(dropOffLocation, outListYellowDto.getDropOffLocation());
    }

    @Test
    void Given_FetchYellowByParam_When_NotExistsYellowTaxis_Then_DontReturnsYellowTaxis() {
        String pickupDatetime = "2023-01-01T00:25:00";
        String dropOffDatetime = "2023-01-01T00:37:49";
        Long pickupLocation = 48L;
        Long dropOffLocation = 238L;

        Page<OutListYellowDto> outListYellowDtoPage = fetchYellowUseCase.fetchYellowByParam(null, pickupDatetime, dropOffDatetime, pickupLocation, dropOffLocation, Pageable.unpaged());
        assertEquals(0, outListYellowDtoPage.getTotalElements());
    }

    @Test
    void Given_FetchYellowByParam_When_NotPassingFilters_Then_ReturnsAllYellowTaxisPaginated() {
        Page<OutListYellowDto> outListYellowDtoPage = fetchYellowUseCase.fetchYellowByParam(null, null, null, null, null, Pageable.unpaged());
        assertEquals(930, outListYellowDtoPage.getTotalElements());
    }

    @Test
    void Given_FetchYellowByParam_When_PassingFilterRegardingPickUpLocation_Then_ReturnsOnlyYellowTaxisTheSamePickUpLocationPaginated() {
        Long pickupLocation = 48L;

        Page<OutListYellowDto> outListYellowDtoPage = fetchYellowUseCase.fetchYellowByParam(null, null, null, pickupLocation, null, Pageable.unpaged());
        assertEquals(18, outListYellowDtoPage.getTotalElements());

        outListYellowDtoPage.stream()
                .forEach(outListYellowDto -> {
                    assertEquals(pickupLocation, outListYellowDto.getPickupLocation());
                });
    }

    @Test
    void Given_FetchYellowByParam_When_PassingFilterRegardingDropOffLocation_Then_ReturnsOnlyYellowTaxisTheSameDropOffLocationPaginated() {
        Long dropOffLocation = 238L;

        Page<OutListYellowDto> outListYellowDtoPage = fetchYellowUseCase.fetchYellowByParam(null, null, null, null, dropOffLocation, Pageable.unpaged());
        assertEquals(35, outListYellowDtoPage.getTotalElements());

        outListYellowDtoPage.stream()
                .forEach(outListYellowDto -> {
                    assertEquals(dropOffLocation, outListYellowDto.getDropOffLocation());
                });
    }
}