package com.department.transportation.trip.statistics.model.repositories;

import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 12:14
 */
@Repository
public interface ZoneRepository extends JpaRepository<ZoneEntity, Long> {

    Optional<ZoneEntity> findByZone(String zone);
}
