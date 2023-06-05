package com.department.transportation.trip.statistics.core.services;

import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import com.department.transportation.trip.statistics.model.repositories.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:03
 */
@RequiredArgsConstructor
@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @Transactional
    public void save(ZoneEntity zoneEntity) {
        zoneRepository.save(zoneEntity);
    }

    public Optional<ZoneEntity> findOneByIdOrZone(String zone) {

        if (NumberUtils.isParsable(zone)) {
            Optional<ZoneEntity> zoneEntityOptional = zoneRepository.findById(Long.valueOf(zone));

            if (zoneEntityOptional.isPresent()) {
                return zoneEntityOptional;
            }
        }

        return zoneRepository.findByZone(zone);
    }
}
