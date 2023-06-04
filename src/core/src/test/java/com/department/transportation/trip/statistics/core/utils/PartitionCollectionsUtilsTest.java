package com.department.transportation.trip.statistics.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 19:09
 */
class PartitionCollectionsUtilsTest {
    @Test
    void Given_List_When_PartitionListWithSizeBlockLimited_Then_ReturnPartitions() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<List<Integer>> partitions = PartitionCollectionsUtils.partitionListWithSizeBlockLimited(list, 3);

        Assertions.assertEquals(4, partitions.size());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), partitions.get(0));
        Assertions.assertEquals(Arrays.asList(4, 5, 6), partitions.get(1));
        Assertions.assertEquals(Arrays.asList(7, 8, 9), partitions.get(2));
        Assertions.assertEquals(List.of(10), partitions.get(3));
    }

    @Test
    void Given_List_When_PartitionListWithTotalParts_Then_ReturnPartitions() {
        List<String[]> list = Arrays.asList(
                new String[]{"A", "B", "C"},
                new String[]{"D", "E", "F"},
                new String[]{"G", "H", "I"},
                new String[]{"J", "K", "L"},
                new String[]{"M", "N", "O"}
        );

        List<List<String[]>> partitions = PartitionCollectionsUtils.partitionListWithTotalParts(list, 3);

        Assertions.assertEquals(3, partitions.size());
        Assertions.assertArrayEquals(new String[][]{
                new String[]{"A", "B", "C"},
                new String[]{"D", "E", "F"}
        }, partitions.get(0).toArray(new String[0][]));
        Assertions.assertArrayEquals(new String[][]{
                new String[]{"G", "H", "I"},
                new String[]{"J", "K", "L"}
        }, partitions.get(1).toArray(new String[0][]));
        Assertions.assertArrayEquals(new String[][]{
                new String[]{"M", "N", "O"}
        }, partitions.get(2).toArray(new String[0][]));
    }
}