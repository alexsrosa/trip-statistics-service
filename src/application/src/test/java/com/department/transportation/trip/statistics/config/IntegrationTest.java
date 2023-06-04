package com.department.transportation.trip.statistics.config;

import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 14:30
 */
@ActiveProfiles({"test"})
@Import(H2TestConfiguration.class)
@TestInstance(PER_CLASS)
public abstract class IntegrationTest {

}
