package com.department.transportation.trip.statistics.core.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 13:50
 */
@UtilityClass
public class PartitionCollectionsUtils {

    public <T> List<List<T>> partitionListWithSizeBlockLimited(List<T> list, int blockSize) {
        return IntStream.range(0, (list.size() + blockSize - 1) / blockSize)
                .mapToObj(i -> list.subList(i * blockSize, Math.min((i + 1) * blockSize, list.size())))
                .toList();
    }

    public List<List<String[]>> partitionListWithTotalParts(List<String[]> list, int totalParts) {
        List<List<String[]>> dividedList = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) list.size() / totalParts);

        for (int i = 0; i < list.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, list.size());
            List<String[]> part = list.subList(i, end);
            dividedList.add(part);
        }

        return dividedList;
    }
}
