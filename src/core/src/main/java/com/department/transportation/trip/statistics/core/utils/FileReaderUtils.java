package com.department.transportation.trip.statistics.core.utils;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 03/06/2023 17:01
 */
@UtilityClass
public class FileReaderUtils {

    public int readFileOnResources(String resourceName, Consumer<String> function, boolean skipFirstLine) throws FileNotFoundException {

        if (isNull(resourceName)) {
            throw new IllegalArgumentException();
        }

        int totalLines = 0;

        try (InputStream in = FileReaderUtils.class.getClassLoader().getResourceAsStream(resourceName)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                try (BufferedReader br = new BufferedReader(reader)) {

                    String line = br.readLine();

                    if (skipFirstLine) {
                        line = br.readLine();
                    }

                    while (line != null) {
                        function.accept(line);
                        totalLines++;
                        line = br.readLine();
                    }
                }
            }
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        return totalLines;
    }
}
