package com.department.transportation.trip.statistics.model.entities;

import com.department.transportation.trip.statistics.api.dtos.OutTopZonesDto;
import com.department.transportation.trip.statistics.api.dtos.OutZoneTripDto;
import com.department.transportation.trip.statistics.model.enums.TaxisTypeEnum;
import com.department.transportation.trip.statistics.model.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 12:43
 */
@NamedNativeQuery(name = "FindTop5ZonesOrderByPickups", query = """
        SELECT report.zone_name, report.pickup, (SELECT COUNT(*) FROM taxis JOIN zones ON zones.zone = report.zone_name AND zones.id = taxis.drop_off_location_id) AS dropoff
        FROM (
            SELECT zones.zone AS zone_name, count(*) AS pickup
            FROM taxis JOIN zones ON zones.id = taxis.pickup_location_id GROUP BY zones.zone
            ORDER BY COUNT(*) DESC limit 5
        ) AS report
        """, resultSetMapping = "OutTopZonesDto")
@NamedNativeQuery(name = "FindTop5ZonesOrderByDropOff", query = """
        SELECT report.zone_name, report.dropoff, (SELECT COUNT(*) FROM taxis JOIN zones ON zones.zone = report.zone_name AND zones.id = taxis.pickup_location_id) AS pickup
        FROM (
            SELECT zones.zone AS zone_name, count(*) AS dropoff
            FROM taxis JOIN zones ON zones.id = taxis.drop_off_location_id GROUP BY zones.zone
            ORDER BY count(*) DESC limit 5
        ) AS report
        """, resultSetMapping = "OutTopZonesDto")
@SqlResultSetMapping(name = "OutTopZonesDto", classes = {@ConstructorResult(targetClass = OutTopZonesDto.class,
        columns = {
                @ColumnResult(name = "zone_name", type = String.class),
                @ColumnResult(name = "pickup", type = Long.class),
                @ColumnResult(name = "dropoff", type = Long.class)}
)})
@NamedNativeQuery(name = "fetchZoneTripsSumsByZoneIdAndDate", query = """
        SELECT
        (SELECT count(*) FROM taxis WHERE taxis.drop_off_location_id = :zoneId AND date_trunc('day', taxis.drop_off_datetime) = :date ) AS dropoffs,
        (SELECT count(*) FROM taxis WHERE taxis.pickup_location_id = :zoneId AND date_trunc('day', taxis.pickup_datetime) = :date) AS pickups
        """, resultSetMapping = "OutZoneTripDto")
@SqlResultSetMapping(name = "OutZoneTripDto", classes = {@ConstructorResult(targetClass = OutZoneTripDto.class,
        columns = {
                @ColumnResult(name = "dropoffs", type = Long.class),
                @ColumnResult(name = "pickups", type = Long.class),
        })})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "taxis")
public class TaxisEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime pickupDatetime;

    @Column(nullable = false)
    private LocalDateTime dropOffDatetime;

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private TaxisTypeEnum taxisType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ZoneEntity pickupLocation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ZoneEntity dropOffLocation;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "pickupDatetime = " + pickupDatetime + ", " +
                "dropOffDatetime = " + dropOffDatetime + ", " +
                "taxisType = " + taxisType + ")";
    }
}
