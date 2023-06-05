package com.department.transportation.trip.statistics.controllers;

import com.department.transportation.trip.statistics.config.TestConfiguration;
import com.department.transportation.trip.statistics.core.usecases.ImportTaxisUseCase;
import com.department.transportation.trip.statistics.core.usecases.ImportZonesUseCase;
import com.department.transportation.trip.statistics.model.repositories.TaxisRepository;
import com.department.transportation.trip.statistics.model.repositories.ZoneRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 19:03
 */
@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockServletContext.class, TestConfiguration.class})
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
class TripStatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ImportZonesUseCase importZonesUseCase;

    @Autowired
    private ImportTaxisUseCase importTaxisUseCase;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private TaxisRepository taxisRepository;

    @BeforeAll
    void initTest() {
        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();
    }

    @AfterAll
    void destroy() {
        taxisRepository.deleteAllInBatch();
        zoneRepository.deleteAllInBatch();
    }

    // GET # /v1/trips/top_zones

    @Test
    void Given_EndpointTopZone_When_NoInformOrder_Then_ReturnBadRequest() throws Exception {
        mvc.perform(get("/v1/trips/top_zones"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages").value("order must not be blank"))
                .andDo(print());
    }

    @Test
    void Given_EndpointTopZone_When_InformOrderDropOffs_Then_ReturnTopZoneOrderedByDropOffs() throws Exception {
        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();

        mvc.perform(get("/v1/trips/top_zones")
                        .param("order", "dropoffs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.top_zones").isNotEmpty())
                .andExpect(jsonPath("$.top_zones.size()").value(5))
                .andExpect(jsonPath("$.top_zones[0].zone").value("Upper East Side North"))
                .andExpect(jsonPath("$.top_zones[0].pu_total").value(38))
                .andExpect(jsonPath("$.top_zones[0].do_total").value(49))
                .andExpect(jsonPath("$.top_zones[1].zone").value("Upper West Side North"))
                .andExpect(jsonPath("$.top_zones[1].pu_total").value(30))
                .andExpect(jsonPath("$.top_zones[1].do_total").value(45))
                .andDo(print());
    }

    @Test
    void Given_EndpointTopZone_When_InformOrderPickups_Then_ReturnTopZoneOrderedByPickups() throws Exception {
        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();

        mvc.perform(get("/v1/trips/top_zones")
                        .param("order", "pickups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.top_zones").isNotEmpty())
                .andExpect(jsonPath("$.top_zones.size()").value(5))
                .andExpect(jsonPath("$.top_zones[0].zone").value("East Village"))
                .andExpect(jsonPath("$.top_zones[0].pu_total").value(56))
                .andExpect(jsonPath("$.top_zones[0].do_total").value(30))
                .andExpect(jsonPath("$.top_zones[1].zone").value("East Harlem North"))
                .andExpect(jsonPath("$.top_zones[1].pu_total").value(54))
                .andExpect(jsonPath("$.top_zones[1].do_total").value(22))
                .andDo(print());
    }


    // GET # /v1/trips/zone-trips

    @Test
    void Given_EndpointZoneTrip_When_InformInvalidValue_Then_ReturnBadRequest() throws Exception {
        mvc.perform(get("/v1/trips/zone-trips"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages[0]").value("date must not be null"))
                .andExpect(jsonPath("$.messages[1]").value("zone must not be blank"))
                .andDo(print());

        mvc.perform(get("/v1/trips/zone-trips")
                        .param("zone", "East Village"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages[0]").value("date must not be null"))
                .andDo(print());

        mvc.perform(get("/v1/trips/zone-trips")
                        .param("date", "2023-06-05"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages[0]").value("zone must not be blank"))
                .andDo(print());

        mvc.perform(get("/v1/trips/zone-trips")
                        .param("zone", "East Village")
                        .param("date", "20-06-2023"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages[0]").value("date Invalid type"))
                .andDo(print());

    }

    @Test
    void Given_EndpointZoneTrip_When_InformValidValueWithZoneByString_Then_ReturnCorrectlyObject() throws Exception {
        mvc.perform(get("/v1/trips/zone-trips")
                        .param("zone", "East Village")
                        .param("date", "2023-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.zone").value("East Village"))
                .andExpect(jsonPath("$.date").value("2023-01-01"))
                .andExpect(jsonPath("$.pu").value("30"))
                .andExpect(jsonPath("$.do").value("56"))
                .andDo(print());
    }

    @Test
    void Given_EndpointZoneTrip_When_InformValidValueWithZoneById_Then_ReturnCorrectlyObject() throws Exception {
        mvc.perform(get("/v1/trips/zone-trips")
                        .param("zone", "79")
                        .param("date", "2023-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.zone").value("East Village"))
                .andExpect(jsonPath("$.date").value("2023-01-01"))
                .andExpect(jsonPath("$.pu").value("30"))
                .andExpect(jsonPath("$.do").value("56"))
                .andDo(print());
    }


    // GET # /v1/trips/list-yellow

    @Test
    void Given_EndpointListYellow_When_NoInformFilters_Then_ReturnListYellowWithPageable() throws Exception {
        mvc.perform(get("/v1/trips/list-yellow"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(20))
                .andExpect(jsonPath("$.totalElements").value(930))
                .andExpect(jsonPath("$.content[0].id").isString())
                .andExpect(jsonPath("$.content[0].pu_date").value("2023-01-01T00:32:10"))
                .andExpect(jsonPath("$.content[0].do_date").value("2023-01-01T00:40:36"))
                .andExpect(jsonPath("$.content[0].pu_location").value(161))
                .andExpect(jsonPath("$.content[0].do_location").value(141))
                .andDo(print());
    }

    @Test
    void Given_EndpointListYellow_When_WithFilterByPuLocation_Then_ReturnListYellowWithPageable() throws Exception {

        mvc.perform(get("/v1/trips/list-yellow")
                        .param("pu_location", "161"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(20))
                .andExpect(jsonPath("$.totalElements").value(36))
                .andExpect(jsonPath("$.content[0].id").isString())
                .andExpect(jsonPath("$.content[0].pu_date").value("2023-01-01T00:32:10"))
                .andExpect(jsonPath("$.content[0].do_date").value("2023-01-01T00:40:36"))
                .andExpect(jsonPath("$.content[0].pu_location").value(161))
                .andExpect(jsonPath("$.content[0].do_location").value(141))
                .andDo(print());
    }

    @Test
    void Given_EndpointListYellow_When_WithFilterByAllFilters_Then_ReturnListYellowWithPageable() throws Exception {

        mvc.perform(get("/v1/trips/list-yellow")
                        .param("pu_date", "2023-01-01T00:32:10")
                        .param("do_date", "2023-01-01T00:40:36")
                        .param("pu_location", "161")
                        .param("do_location", "141")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").isString())
                .andExpect(jsonPath("$.content[0].pu_date").value("2023-01-01T00:32:10"))
                .andExpect(jsonPath("$.content[0].do_date").value("2023-01-01T00:40:36"))
                .andExpect(jsonPath("$.content[0].pu_location").value(161))
                .andExpect(jsonPath("$.content[0].do_location").value(141))
                .andDo(print());
    }
}