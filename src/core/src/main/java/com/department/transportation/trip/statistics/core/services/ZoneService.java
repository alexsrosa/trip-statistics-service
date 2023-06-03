package com.department.transportation.trip.statistics.core.services;

import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import com.department.transportation.trip.statistics.model.repositories.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:03
 */
@RequiredArgsConstructor
@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public void deleteAll() {
        zoneRepository.deleteAll();
    }

    public void save(ZoneEntity entity) {
        zoneRepository.save(entity);
    }
}
