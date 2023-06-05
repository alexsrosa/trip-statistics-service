package com.department.transportation.trip.statistics.core.usecases;

import com.department.transportation.trip.statistics.core.mappers.TaxisMapper;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import com.department.transportation.trip.statistics.core.utils.CSVReaderUtils;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.repositories.TaxisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 18:55
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ImportTaxisUseCase {
    private final TaxisRepository taxisRepository;

    private final Consumer<List<String[]>> actionSaveNewGreenTaxis = this::saveGreenByArrayString;
    private final Consumer<List<String[]>> actionSaveNewYellowTaxis = this::saveYellowByArrayString;

    private final Map<String, Consumer<List<String[]>>> filesToRead = Map.of("green", actionSaveNewGreenTaxis, "yellow", actionSaveNewYellowTaxis);

    private final TaxisService taxisService;
    private final TopZonesUseCase topZonesUseCase;
    private final ZoneTripUseCase zoneTripUseCase;

    public void importTaxis() {
        log.info(">> Starts to load taxis!");

        log.info("Clean dataset taxis");
        taxisService.deleteAll();

        filesToRead.forEach((fileName, actionConsumer) -> {
            log.info("Starts reading {} taxis", fileName);
            long totalLines = CSVReaderUtils.processCsvWithSkipFirstLineAndBlockSizeWith5000("files/" + fileName, actionConsumer);
            log.info("{} {} taxis were imported", totalLines, fileName);
        });

        topZonesUseCase.evictCaches();
        zoneTripUseCase.evictCaches();

        log.info("<< Load taxis finished.");
    }

    private void saveGreenByArrayString(List<String[]> columnsList) {
        saveAll(TaxisMapper.mapGreenToDbo.apply(columnsList));
    }

    private void saveYellowByArrayString(List<String[]> columnsList) {
        saveAll(TaxisMapper.mapYellowToDbo.apply(columnsList));
    }

    private void saveAll(List<TaxisEntity> taxisEntityList) {
        taxisRepository.saveAll(taxisEntityList);
    }
}
