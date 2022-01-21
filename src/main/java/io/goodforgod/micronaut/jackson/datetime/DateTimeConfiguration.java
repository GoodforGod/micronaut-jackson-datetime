package io.goodforgod.micronaut.jackson.datetime;

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isJavaIsoByDefault() {
        return javaIsoByDefault;
    }

    public void setJavaIsoByDefault(boolean javaIsoByDefault) {
        this.javaIsoByDefault = javaIsoByDefault;
    }
}
