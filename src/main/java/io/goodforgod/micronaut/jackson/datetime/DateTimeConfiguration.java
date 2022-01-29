package io.goodforgod.micronaut.jackson.datetime;

import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModule;
import io.goodforgod.jackson.module.datetime.configuration.JavaTimeModuleConfiguration;
import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2022
 */
@ConfigurationProperties("jackson.datetime")
public class DateTimeConfiguration {

    private boolean enabled = true;
    private boolean javaIsoByDefault = false;

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables JavaTimeModule {@link JavaTimeModule} to register
     * 
     * @param enabled true to enable
     */
    public void setEnabled(boolean enabled) {
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
    public void setJavaIsoByDefault(boolean javaIsoByDefault) {
        this.javaIsoByDefault = javaIsoByDefault;
    }
}
