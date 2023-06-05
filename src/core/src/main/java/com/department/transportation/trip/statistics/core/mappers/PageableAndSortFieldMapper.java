package com.department.transportation.trip.statistics.core.mappers;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 23:52
 */
@Slf4j
@UtilityClass
public class PageableAndSortFieldMapper {
    public static Pageable map(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return pageable;
        }

        Sort sort = null;
        for (Sort.Order order : pageable.getSort()) {
            String mappedSortPropertyName = mapSortProperty(order.getProperty());
            if (nonNull(mappedSortPropertyName)) {
                if (isNull(sort)) {
                    sort = Sort.by(order.getDirection(), mappedSortPropertyName);
                } else {
                    sort.and(Sort.by(order.getDirection(), mappedSortPropertyName));
                }
            } else {
                log.warn("Ignored sort {}", order);
            }
        }

        if (isNull(sort)) {
            sort = Sort.unsorted();
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    private static String mapSortProperty(String sortPropertyName) {
        return switch (sortPropertyName) {
            case "id" -> "id";
            case "pu_date" -> "pickupDatetime";
            case "do_date" -> "dropOffDatetime";
            case "pu_location" -> "pickupLocation";
            case "do_location" -> "dropOffLocation";
            default -> null;
        };
    }
}
