package com.external.yml;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.external.configuration.ExternalDataSourceConfiguration.EXTERNAL_JPA_PREFIX;

@Component
@ConfigurationProperties(prefix = EXTERNAL_JPA_PREFIX)
public class ExternalJpaProperty extends JpaProperties {

}
