package com.department.transportation.trip.statistics.model.repositories;

import com.department.transportation.trip.statistics.api.OutTopZonesDto;
import com.department.transportation.trip.statistics.api.OutZoneTripDto;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 16:29
 */
@Repository
public interface TaxisRepository extends JpaSpecificationExecutor<TaxisEntity>, JpaRepository<TaxisEntity, Long> {

    @Query(name = "FindTop5ZonesOrderByPickups", nativeQuery = true)
    List<OutTopZonesDto> findTop5ZonesOrderByPickups();

    @Query(name = "FindTop5ZonesOrderByDropOff", nativeQuery = true)
    List<OutTopZonesDto> findTop5ZonesOrderByDropOff();

    @Query(name = "fetchZoneTripsSumsByZoneIdAndDate", nativeQuery = true)
    OutZoneTripDto fetchZoneTripsSumsByZoneIdAndDate(Long zoneId, LocalDate date);
}
