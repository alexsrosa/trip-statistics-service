package com.department.transportation.trip.statistics.core.usecases;

import com.department.transportation.trip.statistics.api.dtos.OutZoneTripDto;
import com.department.transportation.trip.statistics.api.queryparams.InZoneTripsQueryParam;
import com.department.transportation.trip.statistics.core.config.TestConfiguration;
import com.department.transportation.trip.statistics.core.exceptions.ZoneNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 18:39
 */
@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockServletContext.class, TestConfiguration.class})
@Sql({"/sql/import_clean_all_data.sql"})
class ZoneTripUseCaseTest {

    @Autowired
    private ZoneTripUseCase zoneTripUseCase;

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
    void Given_FetchZoneTripsWithFilter_When_ExistsZoneTripsWithFilter_Then_ReturnsZoneTripsWithFilter() {

        InZoneTripsQueryParam inZoneTripsQueryParam = InZoneTripsQueryParam.builder().zone("238").date(LocalDate.of(2023, 1, 1)).build();
        OutZoneTripDto outZoneTripDto = zoneTripUseCase.fetchZoneTripsWithFilter(inZoneTripsQueryParam);

        assertNotNull(outZoneTripDto);
        assertEquals("Upper West Side North", outZoneTripDto.getZone());
        assertEquals(inZoneTripsQueryParam.getDate(), outZoneTripDto.getDate());
        assertEquals(45L, outZoneTripDto.getPickUps());
        assertEquals(30L, outZoneTripDto.getDropOffs());
    }

    @Test
    void Given_FetchZoneTripsWithFilter_When_ExistsZoneButNoTripsWithFilter_Then_ReturnsObjectWithPickUpAndDropOffWithZero() {

        InZoneTripsQueryParam inZoneTripsQueryParam = InZoneTripsQueryParam.builder().zone("238").date(LocalDate.of(2023, 1, 2)).build();
        OutZoneTripDto outZoneTripDto = zoneTripUseCase.fetchZoneTripsWithFilter(inZoneTripsQueryParam);

        assertNotNull(outZoneTripDto);
        assertEquals("Upper West Side North", outZoneTripDto.getZone());
        assertEquals(inZoneTripsQueryParam.getDate(), outZoneTripDto.getDate());
        assertEquals(0L, outZoneTripDto.getPickUps());
        assertEquals(0L, outZoneTripDto.getDropOffs());
    }

    @Test
    void Given_FetchZoneTripsWithFilter_When_NotExistsZoneAndNoTripsWithFilter_Then_ThrowsZoneNotFoundException() {
        InZoneTripsQueryParam inZoneTripsQueryParam = InZoneTripsQueryParam.builder().zone("999").date(LocalDate.of(2023, 1, 2)).build();
        Exception exception = assertThrows(ZoneNotFoundException.class, () -> zoneTripUseCase.fetchZoneTripsWithFilter(inZoneTripsQueryParam));
        assertEquals(exception.getMessage(), "Zone Not Found");
    }
}