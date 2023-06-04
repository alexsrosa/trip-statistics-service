package com.department.transportation.trip.statistics.core.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 13:50
 */
@UtilityClass
public class PartitionCollectionsUtils {

    public <T> List<List<T>> partitionByBlock(List<T> list, int blockSize) {
        return IntStream.range(0, (list.size() + blockSize - 1) / blockSize)
                .mapToObj(i -> list.subList(i * blockSize, Math.min((i + 1) * blockSize, list.size())))
                .toList();
    }
}
