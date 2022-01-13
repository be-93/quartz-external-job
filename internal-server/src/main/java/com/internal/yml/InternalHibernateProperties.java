package com.internal.yml;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.internal.configuration.InternalDataSourceConfiguration.INTERNAL_JPA_HIBERNATE_PREFIX;

@Configuration
@ConfigurationProperties(prefix = INTERNAL_JPA_HIBERNATE_PREFIX)
public class InternalHibernateProperties extends HibernateProperties {
}
