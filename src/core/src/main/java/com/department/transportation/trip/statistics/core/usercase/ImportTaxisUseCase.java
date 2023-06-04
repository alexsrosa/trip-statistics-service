package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.core.mappers.TaxisMapper;
import com.department.transportation.trip.statistics.core.services.TaxisService;
import com.department.transportation.trip.statistics.core.utils.CSVReaderUtils;
import com.department.transportation.trip.statistics.core.utils.PartitionCollectionsUtils;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
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

    private final Consumer<List<String[]>> actionSaveNewGreenTaxis = this::saveGreenByString;
    private final Consumer<List<String[]>> actionSaveNewYellowTaxis = this::saveYellowByString;

    private final Map<String, Consumer<List<String[]>>> filesToRead = Map.of("green", actionSaveNewGreenTaxis, "yellow", actionSaveNewYellowTaxis);

    private final TaxisService taxisService;

    public void importTaxis() {
        log.info(">> Starts to load taxis!");

        log.info("Clean dataset taxis");
        taxisService.deleteAll();

        filesToRead.forEach((fileName, actionConsumer) -> {
            log.info("Starts reading {} taxis", fileName);
            long totalLines = CSVReaderUtils.processRead("files/" + fileName + ".csv", actionConsumer, true);
            log.info("{} {} taxis were imported", totalLines, fileName);
        });

        log.info("<< Load yellow finished.");
    }

    private void saveGreenByString(List<String[]> columnsList) {
        saveAll(TaxisMapper.mapGreenToDbo.apply(columnsList));
    }

    private void saveYellowByString(List<String[]> columnsList) {
        saveAll(TaxisMapper.mapYellowToDbo.apply(columnsList));
    }

    private void saveAll(List<TaxisEntity> taxisEntityList) {
        PartitionCollectionsUtils.partitionListWithSizeBlockLimited(taxisEntityList, 5000)
                .parallelStream()
                .forEach(taxisService::saveAll);
    }
}
