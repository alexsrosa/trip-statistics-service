package com.department.transportation.trip.statistics.model.entities;

import com.department.transportation.trip.statistics.model.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 12:43
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "taxis")
public class TaxisEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime pickupDatetime;

    @Column(nullable = false)
    private LocalDateTime dropOffDatetime;

    @ManyToOne(optional = false)
    private ZoneEntity pickupLocation;

    @ManyToOne(optional = false)
    private ZoneEntity dropOffLocation;
}
