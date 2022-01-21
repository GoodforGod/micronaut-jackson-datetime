package io.goodforgod.micronaut.jackson.datetime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import io.micronaut.context.annotation.*;
import io.micronaut.core.reflect.GenericTypeUtils;
import io.micronaut.core.util.StringUtils;
import io.micronaut.jackson.JacksonConfiguration;
import io.micronaut.jackson.ObjectMapperFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
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

    private static final String JACKSON_JSR10_MODULE_NAME = "jackson-datatype-jsr310";

    @Override
    public ObjectMapper objectMapper(JacksonConfiguration jacksonConfiguration, JsonFactory jsonFactory) {
        final ObjectMapper objectMapper = jsonFactory != null
                ? new ObjectMapper(jsonFactory)
                : new ObjectMapper();

        final boolean hasConfiguration = jacksonConfiguration != null;
        if (!hasConfiguration || jacksonConfiguration.isModuleScan()) {
            final List<Module> modules = ObjectMapper.findModules().stream()
                    .filter(m -> !m.getModuleName().equals(JACKSON_JSR10_MODULE_NAME))
                    .collect(Collectors.toList());

            objectMapper.registerModules(modules);
        }

        objectMapper.registerModules(jacksonModules);
        SimpleModule module = new SimpleModule(MICRONAUT_MODULE);
        for (JsonSerializer serializer : serializers) {
            Class<? extends JsonSerializer> type = serializer.getClass();
            Type annotation = type.getAnnotation(Type.class);
            if (annotation != null) {
                Class[] value = annotation.value();
                for (Class aClass : value) {
                    module.addSerializer(aClass, serializer);
                }
            } else {
                Optional<Class> targetType = GenericTypeUtils.resolveSuperGenericTypeArgument(type);
                if (targetType.isPresent()) {
                    module.addSerializer(targetType.get(), serializer);
                } else {
                    module.addSerializer(serializer);
                }
            }
        }

        for (JsonDeserializer deserializer : deserializers) {
            Class<? extends JsonDeserializer> type = deserializer.getClass();
            Type annotation = type.getAnnotation(Type.class);
            if (annotation != null) {
                Class[] value = annotation.value();
                for (Class aClass : value) {
                    module.addDeserializer(aClass, deserializer);
                }
            } else {
                Optional<Class> targetType = GenericTypeUtils.resolveSuperGenericTypeArgument(type);
                targetType.ifPresent(aClass -> module.addDeserializer(aClass, deserializer));
            }
        }

        if (hasConfiguration && jacksonConfiguration.isTrimStrings()) {
            module.addDeserializer(String.class, new StringDeserializer() {

                @Override
                public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    String value = super.deserialize(p, ctxt);
                    return StringUtils.trimToNull(value);
                }
            });
        }

        for (KeyDeserializer keyDeserializer : keyDeserializers) {
            Class<? extends KeyDeserializer> type = keyDeserializer.getClass();
            Type annotation = type.getAnnotation(Type.class);
            if (annotation != null) {
                Class[] value = annotation.value();
                for (Class clazz : value) {
                    module.addKeyDeserializer(clazz, keyDeserializer);
                }
            }
        }
        objectMapper.registerModule(module);

        for (BeanSerializerModifier beanSerializerModifier : beanSerializerModifiers) {
            objectMapper.setSerializerFactory(
                    objectMapper.getSerializerFactory().withSerializerModifier(
                            beanSerializerModifier));
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);

        if (hasConfiguration) {
            ObjectMapper.DefaultTyping defaultTyping = jacksonConfiguration.getDefaultTyping();
            if (defaultTyping != null) {
                objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), defaultTyping);
            }

            JsonInclude.Include include = jacksonConfiguration.getSerializationInclusion();
            if (include != null) {
                objectMapper.setSerializationInclusion(include);
            }
            String dateFormat = jacksonConfiguration.getDateFormat();
            if (dateFormat != null) {
                objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
            }
            Locale locale = jacksonConfiguration.getLocale();
            if (locale != null) {
                objectMapper.setLocale(locale);
            }
            TimeZone timeZone = jacksonConfiguration.getTimeZone();
            if (timeZone != null) {
                objectMapper.setTimeZone(timeZone);
            }
            PropertyNamingStrategy propertyNamingStrategy = jacksonConfiguration.getPropertyNamingStrategy();
            if (propertyNamingStrategy != null) {
                objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
            }

            jacksonConfiguration.getSerializationSettings().forEach(objectMapper::configure);
            jacksonConfiguration.getDeserializationSettings().forEach(objectMapper::configure);
            jacksonConfiguration.getMapperSettings().forEach(objectMapper::configure);
            jacksonConfiguration.getParserSettings().forEach(objectMapper::configure);
            jacksonConfiguration.getGeneratorSettings().forEach(objectMapper::configure);
        }
        return objectMapper;
    }
}
