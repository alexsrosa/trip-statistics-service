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
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
        select zone_name, pickup, (select count(z) from taxis t join zones z on z.zone = zone_name and z.id = t.drop_off_location_id) as dropoff
        from (
            select z1.zone as zone_name, count(z1) as pickup
            from taxis t join zones z1 on z1.id = t.pickup_location_id group by z1.zone
            order by count(*) desc limit 5
        ) as report
        """, resultSetMapping = "OutTopZonesDto")
@NamedNativeQuery(name = "FindTop5ZonesOrderByDropOff", query = """
        select zone_name, dropoff, (select count(z) from taxis t join zones z on z.zone = zone_name and z.id = t.pickup_location_id) as pickup
        from (
            select z1.zone as zone_name, count(z1) as dropoff
            from taxis t join zones z1 on z1.id = t.drop_off_location_id group by z1.zone
            order by count(*) desc limit 5
        ) as report
        """, resultSetMapping = "OutTopZonesDto")
@SqlResultSetMapping(name = "OutTopZonesDto", classes = {@ConstructorResult(targetClass = OutTopZonesDto.class,
        columns = {
                @ColumnResult(name = "zone_name", type = String.class),
                @ColumnResult(name = "pickup", type = Long.class),
                @ColumnResult(name = "dropoff", type = Long.class)}
)})
@NamedNativeQuery(name = "fetchZoneTripsSumsByZoneIdAndDate", query = """
        select
        (select count(*) from taxis t where t.drop_off_location_id = :zoneId and date_trunc('day', t.drop_off_datetime) = :date ) as dropoffs,
        (select count(*) from taxis t where t.pickup_location_id = :zoneId and date_trunc('day', t.drop_off_datetime) = :date) as pickups
        """, resultSetMapping = "OutZoneTripDto")
@SqlResultSetMapping(name = "OutZoneTripDto", classes = {@ConstructorResult(targetClass = OutZoneTripDto.class,
        columns = {
                @ColumnResult(name = "dropoffs", type = Long.class),
                @ColumnResult(name = "pickups", type = Long.class),
        })})
@ToString(exclude = {"pickupLocation", "dropOffLocation"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "taxis")
public class TaxisEntity extends BaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime pickupDatetime;

    @Column(nullable = false)
    private LocalDateTime dropOffDatetime;

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private TaxisTypeEnum taxisType;

    @ManyToOne(optional = false)
    private ZoneEntity pickupLocation;

    @ManyToOne(optional = false)
    private ZoneEntity dropOffLocation;
}
