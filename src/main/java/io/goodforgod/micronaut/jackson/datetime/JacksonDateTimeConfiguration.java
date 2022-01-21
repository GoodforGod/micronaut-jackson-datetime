package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModuleConfiguration;

/**
 * @author GoodforGod
 * @since 13.11.2019
 */
public interface JacksonDateTimeConfiguration {

    JavaTimeModuleConfiguration getConfiguration();
}
