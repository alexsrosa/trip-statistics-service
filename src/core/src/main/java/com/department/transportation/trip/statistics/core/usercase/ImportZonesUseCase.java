package com.department.transportation.trip.statistics.core.usercase;

import com.department.transportation.trip.statistics.core.exceptions.ZonesLoadException;
import com.department.transportation.trip.statistics.core.services.ZoneService;
import com.department.transportation.trip.statistics.core.utils.FileReaderUtils;
import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import com.department.transportation.trip.statistics.model.enums.ServiceZoneEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:04
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ImportZonesUseCase {

    private final ZoneService zoneService;
    private final Consumer<String> actionSaveNewZone = this::saveByString;

    public void importZones() {
        log.info(">> Starts to load zones!");
        try {
            int totalLines = FileReaderUtils.readFileOnResources("files/zone.csv", actionSaveNewZone, true);

            log.info("{} zones were imported", totalLines);
        } catch (FileNotFoundException e) {
            throw new ZonesLoadException(e);
        }

        log.info("<< Load zones finished.");
    }

    private void saveByString(String line) {
        String[] zoneLine = line.replace("\"", Strings.EMPTY).split(",");
        try {
            zoneService.save(ZoneEntity.builder()
                    .id(Long.valueOf(zoneLine[0]))
                    .borough(zoneLine[1])
                    .zone(zoneLine[2])
                    .serviceZone(ServiceZoneEnum.getByValue(zoneLine[3]))
                    .build());
        } catch (Exception ex) {
            log.error("Error to load one zone. Details: {}, DataSet: {}", ex.getMessage(), Arrays.toString(zoneLine));
        }
    }
}
