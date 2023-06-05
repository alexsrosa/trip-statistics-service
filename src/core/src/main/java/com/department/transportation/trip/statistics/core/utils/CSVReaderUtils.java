package com.department.transportation.trip.statistics.core.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
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

    public long processCsvWithSkipFirstLineAndBlockSizeWith5000(String resourceName, Consumer<List<String[]>> action) {
        return processReadCsv(resourceName, action, true, 5000);
    }

    public long processReadCsv(String resourceName, Consumer<List<String[]>> action, boolean skipFirstLine, int blockSize) {
        long totalLines = 0L;

        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(Objects.requireNonNull(
                    FileReaderUtils.class.getClassLoader().getResourceAsStream(resourceName + ".csv"))));
            csvReader.skip(skipFirstLine ? 1 : 0);

            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            String[] line;
            List<String[]> lines = new ArrayList<>();

            while (nonNull((line = csvReader.readNext()))) {
                lines.add(line);
                totalLines++;

                if (totalLines % blockSize == 0) {
                    execute(action, executor, lines);
                    lines = new ArrayList<>();
                }
            }

            if (!lines.isEmpty()) {
                execute(action, executor, lines);
            }

            csvReader.close();
            log.info("Total lines to process: {}", totalLines);

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException interruptedException) {
            log.error("Error executing process in threads! Details: {}", interruptedException.getMessage());
            Thread.currentThread().interrupt();
        } catch (IOException | CsvException e) {
            log.error("Error to process csv. Details: {}", e.getMessage());
            e.printStackTrace();
        }

        return totalLines;
    }

    private void execute(Consumer<List<String[]>> action, ExecutorService executor, final List<String[]> lines) {
        executor.execute(() -> action.accept(lines));
    }
}
