package com.department.transportation.trip.statistics.core.usecases;

import com.department.transportation.trip.statistics.api.dtos.OutTopZonesDto;
import com.department.transportation.trip.statistics.api.dtos.OutTopZonesListDto;
import com.department.transportation.trip.statistics.api.queryparams.InTopZonesQueryParam;
import com.department.transportation.trip.statistics.core.config.TestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 18:14
 */
@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockServletContext.class, TestConfiguration.class})
@Sql({"/sql/import_clean_all_data.sql"})
class TopZonesUseCaseTest {

    @Autowired
    private TopZonesUseCase topZonesUseCase;

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
    void Given_FetchTopZoneByOrder_When_ExistsTopZonesOrderByDropOffs_Then_ReturnsTopZonesOrderByDropOffs() {
        OutTopZonesListDto dropOffs = topZonesUseCase.fetchTopZoneByOrder(InTopZonesQueryParam.builder().order("dropoffs").build());

        assertNotNull(dropOffs);
        assertFalse(dropOffs.getTopZones().isEmpty());
        assertEquals(5, dropOffs.getTopZones().size());

        List<OutTopZonesDto> topZones = dropOffs.getTopZones();
        assertEquals(OutTopZonesDto.builder().zone("Upper East Side North").pickupTotal(38L).dropOffTotal(49L).build(), topZones.get(0));
        assertEquals(OutTopZonesDto.builder().zone("Upper West Side North").pickupTotal(30L).dropOffTotal(45L).build(), topZones.get(1));
        assertEquals(OutTopZonesDto.builder().zone("Lenox Hill West").pickupTotal(24L).dropOffTotal(43L).build(), topZones.get(2));
        assertEquals(OutTopZonesDto.builder().zone("Upper West Side South").pickupTotal(40L).dropOffTotal(42L).build(), topZones.get(3));
        assertEquals(OutTopZonesDto.builder().zone("Yorkville West").pickupTotal(38L).dropOffTotal(39L).build(), topZones.get(4));
    }

    @Test
    void Given_FetchTopZoneByOrder_When_ExistsTopZonesOrderByPickups_Then_ReturnsTopZonesOrderByPickups() {
        OutTopZonesListDto pickups = topZonesUseCase.fetchTopZoneByOrder(InTopZonesQueryParam.builder().order("pickups").build());

        assertNotNull(pickups);
        assertFalse(pickups.getTopZones().isEmpty());
        assertEquals(5, pickups.getTopZones().size());

        List<OutTopZonesDto> topZones = pickups.getTopZones();
        assertEquals(OutTopZonesDto.builder().zone("East Village").pickupTotal(56L).dropOffTotal(30L).build(), topZones.get(0));
        assertEquals(OutTopZonesDto.builder().zone("East Harlem North").pickupTotal(54L).dropOffTotal(22L).build(), topZones.get(1));
        assertEquals(OutTopZonesDto.builder().zone("East Harlem South").pickupTotal(49L).dropOffTotal(30L).build(), topZones.get(2));
        assertEquals(OutTopZonesDto.builder().zone("Central Harlem").pickupTotal(44L).dropOffTotal(22L).build(), topZones.get(3));
        assertEquals(OutTopZonesDto.builder().zone("Upper West Side South").pickupTotal(40L).dropOffTotal(42L).build(), topZones.get(4));
    }
}