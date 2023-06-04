package com.department.transportation.trip.statistics.core.usercase;


import com.department.transportation.trip.statistics.config.IntegrationTest;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import com.department.transportation.trip.statistics.model.repositories.ZoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ImportZonesUseCaseTest extends IntegrationTest {

    @Autowired
    private ImportZonesUseCase importZonesUseCase;

    @Autowired
    private ZoneRepository zoneRepository;

    @BeforeEach
    void beforeEach() {
        destroy();
    }

    @AfterEach
    void destroy() {
        zoneRepository.deleteAll();
    }

    @Test
    void Given_WeNeedToLoadZones_When_ExistFileToLoad_Then_LoadHappenCorrectly() {
        importZonesUseCase.importZones();
        List<ZoneEntity> taxisEntities = zoneRepository.findAll();
        assertEquals(265, taxisEntities.size());
    }
}