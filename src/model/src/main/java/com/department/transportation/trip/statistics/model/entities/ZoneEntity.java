package com.department.transportation.trip.statistics.model.entities;

import com.department.transportation.trip.statistics.model.enums.ServiceZoneEnum;
import com.department.transportation.trip.statistics.model.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 12:13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "zones", indexes = {
        @Index(name = "index_zones_zone", columnList = "zone"),
})
public class ZoneEntity extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 40)
    private String borough;

    @Column(nullable = false, length = 100)
    private String zone;

    @Column(nullable = false, length = 11)
    @Enumerated(EnumType.STRING)
    private ServiceZoneEnum serviceZone;

    @OneToMany(mappedBy = "pickupLocation", fetch = FetchType.LAZY)
    private List<TaxisEntity> taxisPickupLocation;

    @OneToMany(mappedBy = "dropOffLocation", fetch = FetchType.LAZY)
    private List<TaxisEntity> taxisDropOffLocation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ZoneEntity that = (ZoneEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "borough = " + borough + ", " +
                "zone = " + zone + ", " +
                "serviceZone = " + serviceZone + ")";
    }
}
