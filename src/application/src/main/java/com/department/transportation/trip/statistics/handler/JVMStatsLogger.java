package com.department.transportation.trip.statistics.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 05/06/2023 13:37
 */
@Slf4j
@Component
public class JVMStatsLogger {

    @PostConstruct
    public void init() {
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        log.info("Memory in use/available:: {} Mb / {} Mb",
                bytesToMegabytes(heapMemoryUsage.getUsed()),
                bytesToMegabytes(heapMemoryUsage.getMax()));

        log.info("Threads size: {}", ManagementFactory.getThreadMXBean().getThreadCount());
        log.info("Number of available processors: {}", ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors());
    }

    private static double bytesToMegabytes(long bytes) {
        return (double) bytes / (1024 * 1024);
    }
}
