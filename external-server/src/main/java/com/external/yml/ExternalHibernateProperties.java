package com.external.yml;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.external.configuration.ExternalDataSourceConfiguration.EXTERNAL_JPA_HIBERNATE_PREFIX;

@Configuration
@ConfigurationProperties(prefix = EXTERNAL_JPA_HIBERNATE_PREFIX)
public class ExternalHibernateProperties extends HibernateProperties {
}
