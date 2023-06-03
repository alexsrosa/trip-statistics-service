package com.department.transportation.trip.statistics.model.repositories;

import com.department.transportation.trip.statistics.model.entities.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 16:29
 */
@Repository
public interface TaxisRepository extends JpaRepository<ZoneEntity, Long> {
}
