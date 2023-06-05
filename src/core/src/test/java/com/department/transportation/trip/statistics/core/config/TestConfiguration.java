package com.department.transportation.trip.statistics.core.config;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 14:31
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.department.transportation.trip.statistics",
        "com.department.transportation.trip.statistics.api",
        "com.department.transportation.trip.statistics.core",
        "com.department.transportation.trip.statistics.model",
})
@EntityScan(basePackages = {"com.department.transportation.trip.statistics.model"})
@EnableJpaRepositories(basePackages = "com.department.transportation.trip.statistics.model.repositories")
@Configuration
@EnableCaching
public class TestConfiguration {

}
