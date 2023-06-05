package com.department.transportation.trip.statistics.core.processor;

import com.department.transportation.trip.statistics.core.usecases.ImportTaxisUseCase;
import com.department.transportation.trip.statistics.core.usecases.ImportZonesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 10:17
 */
@Profile("!test")
@Slf4j
@RequiredArgsConstructor
@Component
public class LoadDataSetsProcessor {

    private final ImportZonesUseCase importZonesUseCase;
    private final ImportTaxisUseCase importTaxisUseCase;

    @EventListener(ApplicationReadyEvent.class)
    public void startProcess() {
        long startTime = System.currentTimeMillis();

        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();

        long endTime = System.currentTimeMillis();
        long elapsedTimeInSeconds = (endTime - startTime) / 1000;
        log.info("<< Total dataset loading processing time: {} seconds ({} minutes)", elapsedTimeInSeconds, elapsedTimeInSeconds / 60);
    }
}
