package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModuleConfiguration;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.01.2022
 */
public interface DateTimeModuleConfiguration {

    JavaTimeModuleConfiguration getConfiguration();
}
