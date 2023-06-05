package com.department.transportation.trip.statistics.model.converters;

import com.department.transportation.trip.statistics.api.OutListYellowDto;
import com.department.transportation.trip.statistics.api.OutTopZonesListDto;
import com.department.transportation.trip.statistics.model.entities.TaxisEntity;
import lombok.experimental.UtilityClass;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.function.Function;

@UtilityClass
public class TaxisConverter {

    private final MapperFacade mapperFacade;


    static {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();

        factory.classMap(TaxisEntity.class, OutListYellowDto.class)
                .field("pickupLocation.id", "pickupLocation")
                .field("dropOffLocation.id", "dropOffLocation")
                .byDefault()
                .register();


        mapperFacade = factory.getMapperFacade();
    }

    public final Function<TaxisEntity, OutListYellowDto> convertDBOToDTO = taxisEntity ->
            mapperFacade.map(taxisEntity, OutListYellowDto.class);
}
