package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModuleConfiguration;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * @author GoodforGod
 * @since 13.11.2019
 */
@ConfigurationProperties("jackson.datetime")
public class JacksonDateTimeISOConfiguration implements JacksonDateTimeConfiguration {

    @ConfigurationBuilder("module")
    private final JavaTimeModuleConfiguration configuration = JavaTimeModuleConfiguration.ofISO();

    @Override
    public JavaTimeModuleConfiguration getConfiguration() {
        return configuration;
    }
}
