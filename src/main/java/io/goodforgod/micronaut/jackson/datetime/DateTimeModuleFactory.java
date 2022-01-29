package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModule;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2022
 */
@Requires(property = "jackson.datetime.enabled", value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
@Factory
public class DateTimeModuleFactory {

    @Singleton
    @Bean
    JavaTimeModule build(DateTimeConfiguration configuration) {
        return configuration.isJavaIsoByDefault()
                ? configuration.getJavaIsoConfiguration().getModule()
                : configuration.getIsoConfiguration().getModule();
    }
}
