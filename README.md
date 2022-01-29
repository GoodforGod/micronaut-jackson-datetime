# Micronaut Jackson Datetime

![Java CI](https://github.com/GoodforGod/micronaut-jackson-datetime/workflows/Java%20CI/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-jackson-datetime&metric=alert_status)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-jackson-datetime)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-jackson-datetime&metric=coverage)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-jackson-datetime)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=GoodforGod_micronaut-jackson-datetime&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=GoodforGod_micronaut-jackson-datetime)

Library provides Micronaut [Jackson Datetime](https://github.com/GoodforGod/jackson-datetime-configuration) module integration and configuration.

You can read more about [module and its configuration here](https://github.com/GoodforGod/jackson-datetime-configuration).

## Dependency :rocket:

Compatible with Java 8+.

**Gradle**
```groovy
implementation "io.goodforgod:micronaut-jackson-datetime:1.0.0"
```

**Maven**
```xml
<dependency>
    <groupId>io.goodforgod</groupId>
    <artifactId>micronaut-jackson-datetime</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

You can read more about [module configuration in details here](https://github.com/GoodforGod/jackson-datetime-configuration#configuration), below is *application.yaml* configuration example:

Comparison Java ISO formatters vs ISO with millis [here](https://github.com/GoodforGod/jackson-datetime-configuration#configuration).

```yaml
jackson:
  datetime:
    enabled: true                                                       # Enable DateTime module registration (default true)
    java-iso-by-default: true                                           # Enabled Java ISO formatters by default (default - ISO with millis)
    module:
      offset-time-format: HH:mm:ss[.SSS]XXX                             # Set OffsetTime formatter (<- default value)
      offset-date-time-format: uuuu-MM-dd'T'HH:mm:ss[.SSS]XXX           # Set OffsetDateTime formatter (<- default value)
      zoned-date-time-format: uuuu-MM-dd'T'HH:mm:ss[.SSS]XXX['['VV']']  # Set ZonedDateTime formatter (<- default value)
      local-date-time-format: uuuu-MM-dd'T'HH:mm:ss[.SSS]               # Set LocalDateTime formatter (<- default value)
      local-date-format: uuuu-MM-dd                                     # Set LocalDate formatter (<- default value)
      local-time-format: HH:mm:ss[.SSS]XXX                              # Set LocalTime formatter (<- default value)
      year-format: uuuu                                                 # Set Year formatter (<- default value)
      year-month-format: uuuu-MM                                        # Set YearMonth formatter (<- default value)
      month-day-format: MM-dd                                           # Set MonthDay formatter (<- default value)
      force-resolver-strict: true                                       # Forces ResolverStyle#STRICT for all formatters (default - false)
      force-iso-chronology: true                                        # Forces IsoChronology for all formatters (default - false)
```

If you want know more about [Java Date & Time you can read article here](https://goodforgod.dev/posts/2/).

### Recommended Configuration

Below is minimal recommended configuration that will be working for most cases.

```yaml
jackson:
  date-format: yyyy-MM-dd'T'HH:mm:ss.SSSXXX     # Old Java formatter time API similar to what this library provides for java.time.*
  serialization:
    write-dates-as-timestamps: false            # Don't write dates as longs
  deserialization:
    adjust-dates-to-context-time-zone: false    # Don't change ZoneOffset to local
  datetime:
    enabled: true                               # Enables date time module registration
```

### Hints

For proper Date & Times serialization, you probably don't want to adjust ZoneOffset when deserializing and serializing as longs, then configure as follows:

```yaml
jackson:
  serialization:
    write-dates-as-timestamps: false            # Don't write dates as longs
  deserialization:
    adjust-dates-to-context-time-zone: false    # Don't change ZoneOffset to local
```

## License

This project licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

