package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModule;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2022
 */
@Requires(property = "jackson.datetime.enabled", value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
@Factory
public class DateTimeModuleFactory {

    private final DateTimeModuleConfiguration dateTimeConfiguration;

    @Inject
    public DateTimeModuleFactory(DateTimeModuleConfiguration dateTimeConfiguration) {
        this.dateTimeConfiguration = dateTimeConfiguration;
    }

    @Singleton
    @Bean
    JavaTimeModule build() {
        return dateTimeConfiguration.getConfiguration().getModule();
    }
}
