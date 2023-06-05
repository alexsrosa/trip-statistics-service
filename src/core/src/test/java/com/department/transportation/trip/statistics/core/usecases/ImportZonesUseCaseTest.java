package com.department.transportation.trip.statistics.core.usecases;


import com.department.transportation.trip.statistics.core.config.TestConfiguration;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import com.department.transportation.trip.statistics.model.repositories.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 15:39
 */
@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockServletContext.class, TestConfiguration.class})
@Sql({"/sql/import_clean_all_data.sql"})
class ImportZonesUseCaseTest {

    @Autowired
    private ImportZonesUseCase importZonesUseCase;

    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void Given_WeNeedToLoadZones_When_ExistFileToLoad_Then_LoadHappenCorrectly() {
        importZonesUseCase.importZones();
        List<ZoneEntity> taxisEntities = zoneRepository.findAll();
        assertEquals(265, taxisEntities.size());
    }
}