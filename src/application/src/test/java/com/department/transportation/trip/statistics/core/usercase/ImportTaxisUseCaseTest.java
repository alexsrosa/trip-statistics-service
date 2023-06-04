package com.department.transportation.trip.statistics.core.usercase;


import com.department.transportation.trip.statistics.config.H2TestConfiguration;
import com.department.transportation.trip.statistics.config.IntegrationTest;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.repositories.TaxisRepository;
import com.department.transportation.trip.statistics.model.repositories.ZoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {MockServletContext.class, H2TestConfiguration.class})
class ImportTaxisUseCaseTest extends IntegrationTest {

    @Autowired
    private ImportTaxisUseCase importTaxisUseCase;

    @Autowired
    private ImportZonesUseCase importZonesUseCase;

    @Autowired
    private TaxisRepository taxisRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @BeforeEach
    void beforeEach() {
        destroy();
        importZonesUseCase.importZones();
    }

    @AfterEach
    void destroy() {
        taxisRepository.deleteAll();
        zoneRepository.deleteAll();
    }

    @Test
    void Given_WeNeedUseCache_When_GetOrderProductsFromOrderId_Then_ShouldUseCache() {
        importTaxisUseCase.importTaxis();
        List<TaxisEntity> taxisEntities = taxisRepository.findAll();
        assertEquals(1427, taxisEntities.size());
    }
}