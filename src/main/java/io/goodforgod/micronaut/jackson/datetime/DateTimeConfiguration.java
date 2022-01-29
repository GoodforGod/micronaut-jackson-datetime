package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModule;
import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModuleConfiguration;
import io.micronaut.context.annotation.ConfigurationBuilder;
import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2022
 */
@ConfigurationProperties("jackson.datetime")
public class DateTimeConfiguration {

    private boolean enabled = true;
    private boolean javaIsoByDefault = false;

    @ConfigurationBuilder("module")
    private final JavaTimeModuleConfiguration isoConfiguration = JavaTimeModuleConfiguration.ofISO();

    @ConfigurationBuilder("module")
    private final JavaTimeModuleConfiguration javaIsoConfiguration = JavaTimeModuleConfiguration.ofJavaISO();

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables JavaTimeModule {@link JavaTimeModule} to register
     * 
     * @param enabled true to enable
     */
    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isJavaIsoByDefault() {
        return javaIsoByDefault;
    }

    /**
     * Enables Java ISO formatters by default {@link JavaTimeModuleConfiguration#ofJavaISO()}
     * 
     * @param javaIsoByDefault true to enable
     */
    void setJavaIsoByDefault(boolean javaIsoByDefault) {
        this.javaIsoByDefault = javaIsoByDefault;
    }

    public JavaTimeModuleConfiguration getIsoConfiguration() {
        return isoConfiguration;
    }

    public JavaTimeModuleConfiguration getJavaIsoConfiguration() {
        return javaIsoConfiguration;
    }
}
