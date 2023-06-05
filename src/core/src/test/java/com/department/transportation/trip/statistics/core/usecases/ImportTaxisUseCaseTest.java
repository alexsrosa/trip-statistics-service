package com.department.transportation.trip.statistics.core.usecases;


import com.department.transportation.trip.statistics.core.config.TestConfiguration;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.repositories.TaxisRepository;
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
 * @since 05/06/2023 17:09
 */
@ActiveProfiles({"test"})
@SpringBootTest(classes = {MockServletContext.class, TestConfiguration.class})
@Sql({"/sql/import_clean_all_data.sql"})
class ImportTaxisUseCaseTest {

    @Autowired
    private ImportTaxisUseCase importTaxisUseCase;

    @Autowired
    private ImportZonesUseCase importZonesUseCase;

    @Autowired
    private TaxisRepository taxisRepository;

    @Test
    void Given_WeNeedUseCache_When_GetOrderProductsFromOrderId_Then_ShouldUseCache() {
        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();
        List<TaxisEntity> taxisEntities = taxisRepository.findAll();
        assertEquals(1427, taxisEntities.size());
    }
}