package com.department.transportation.trip.statistics.core.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 10:51
 */
@Slf4j
@UtilityClass
public class CSVReaderUtils {

    public long processRead(String resourceName, Consumer<List<String[]>> function, boolean skipFirstLine) {

        long totalLines = 0L;

        try {
            File file = new File(URI.create(Objects.requireNonNull(CSVReaderUtils.class.getClassLoader().getResource(resourceName)).toString()));
            CSVReader csvReader = new CSVReader(new FileReader(file));

            // Threads pool configuration
            int numThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            csvReader.skip(skipFirstLine ? 1 : 0);

            String[] line;
            List<String[]> lines = new ArrayList<>();

            while (nonNull((line = csvReader.readNext()))) {
                lines.add(line);
            }

            csvReader.close();

            totalLines = lines.size();
            log.info("Total lines to process: {}", totalLines);

            List<List<String[]>> dividedLinesList = PartitionCollectionsUtils.partitionByBlock(lines, numThreads);
            dividedLinesList.forEach(dividedLines -> executor.execute(() -> function.accept(dividedLines)));

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        } catch (IOException | CsvException | InterruptedException e) {
            e.printStackTrace();
        }

        return totalLines;
    }
}
