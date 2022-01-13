package com.internal.yml;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.internal.configuration.InternalDataSourceConfiguration.INTERNAL_JPA_PREFIX;

@Primary
@Configuration
@ConfigurationProperties(prefix = INTERNAL_JPA_PREFIX)
public class InternalJpaProperty extends JpaProperties {
}
