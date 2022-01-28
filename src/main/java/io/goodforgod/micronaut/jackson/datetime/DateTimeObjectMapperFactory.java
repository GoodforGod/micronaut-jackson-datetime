package io.goodforgod.micronaut.jackson.datetime;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import io.micronaut.context.annotation.*;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.jackson.JacksonConfiguration;
import io.micronaut.jackson.ObjectMapperFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2022
 */
@Requires(property = "jackson.datetime.enabled", value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
@Replaces(ObjectMapperFactory.class)
@Factory
@BootstrapContextCompatible
public class DateTimeObjectMapperFactory extends ObjectMapperFactory {

    /**
     * Same module name for {@link io.goodforgod.jackson.module.datetime.configuration.JavaTimeModule}
     * and {@link com.fasterxml.jackson.datatype.jsr310.JavaTimeModule}
     */
    private static final String JACKSON_JSR10_MODULE_NAME = "jackson-datatype-jsr310";

    /**
     * @param jacksonConfiguration to use for mapper construction
     * @param jsonFactory          factory to use for mapper
     * @return object mapper
     */
    @Override
    public ObjectMapper objectMapper(@Nullable JacksonConfiguration jacksonConfiguration,
                                     @Nullable JsonFactory jsonFactory) {
        final boolean hasConfiguration = jacksonConfiguration != null;

        final ObjectMapper objectMapper;
        if (hasConfiguration) {
            jacksonConfiguration.setModuleScan(false); // so won't scan in super
            objectMapper = super.objectMapper(jacksonConfiguration, jsonFactory);
        } else {
            // mimic default config exists so won't scan modules
            final JacksonConfiguration defaultConfig = new JacksonConfiguration();
            defaultConfig.setModuleScan(false); // so won't scan in super
            objectMapper = super.objectMapper(defaultConfig, jsonFactory);
        }

        // Correct JavaTimeModule is registered in super via injected modules
        if (!hasConfiguration || jacksonConfiguration.isModuleScan()) {
            final List<Module> modules = ObjectMapper.findModules().stream()
                    .filter(m -> !m.getModuleName().equals(JACKSON_JSR10_MODULE_NAME))
                    .collect(Collectors.toList());

            objectMapper.registerModules(modules);
        }

        return objectMapper;
    }
}
