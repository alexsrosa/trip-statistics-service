package com.department.transportation.trip.statistics.core.services;

import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.repositories.TaxisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:03
 */
@RequiredArgsConstructor
@Service
public class TaxisService {

    private final TaxisRepository taxisRepository;

    public void deleteAll() {
        taxisRepository.deleteAllInBatch();
    }

    @Transactional
    public void saveAll(List<TaxisEntity> taxisEntityList) {
        taxisRepository.saveAll(taxisEntityList);
    }
}
