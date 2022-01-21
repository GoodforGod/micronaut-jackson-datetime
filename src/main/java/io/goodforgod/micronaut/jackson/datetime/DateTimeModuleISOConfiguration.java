package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModuleConfiguration;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2022
 */
@Requires(property = "jackson.datetime.enabled", value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
@Requires(property = "jackson.datetime.java-iso-enabled", value = StringUtils.FALSE, defaultValue = StringUtils.FALSE)
@ConfigurationProperties("jackson.datetime")
public class DateTimeModuleISOConfiguration implements DateTimeModuleConfiguration {

    @ConfigurationBuilder("module")
    private final JavaTimeModuleConfiguration configuration = JavaTimeModuleConfiguration.ofISO();

    @Override
    public JavaTimeModuleConfiguration getConfiguration() {
        return configuration;
    }
}
