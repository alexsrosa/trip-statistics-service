package com.department.transportation.trip.statistics.core.processor;

import com.department.transportation.trip.statistics.core.usercase.ImportTaxisUseCase;
import com.department.transportation.trip.statistics.core.usercase.ImportZonesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 10:17
 */
@Profile("!test")
@RequiredArgsConstructor
@Component
public class LoadDataSetsProcessor {

    private final ImportZonesUseCase importZonesUseCase;
    private final ImportTaxisUseCase importTaxisUseCase;

    @PostConstruct
    public void init() {
        importZonesUseCase.importZones();
        importTaxisUseCase.importTaxis();
    }
}
