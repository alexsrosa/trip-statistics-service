package com.department.transportation.trip.statistics.core.services;

import com.department.transportation.trip.statistics.api.OutTopZonesDto;
import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import com.department.transportation.trip.statistics.model.repositories.TaxisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public Page<TaxisEntity> fetchTaxisByFilterAndPageable(Example<TaxisEntity> taxisEntityExample, Pageable pageable) {
        return taxisRepository.findAll(taxisEntityExample, pageable);
    }

    public List<OutTopZonesDto> findTop5ZonesOrderByPickups() {
        return taxisRepository.findTop5ZonesOrderByPickups();
    }

    public List<OutTopZonesDto> findTop5ZonesOrderByDropOff() {
        return taxisRepository.findTop5ZonesOrderByDropOff();
    }

    public OutZoneTripDto fetchZoneTripsSumsByZoneIdAndDate(Long idZone, LocalDate date) {
        return taxisRepository.fetchZoneTripsSumsByZoneIdAndDate(idZone, date);
    }
}
