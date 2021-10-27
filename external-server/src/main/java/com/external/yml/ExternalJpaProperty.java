package com.external.yml;

import com.core.yml.JpaProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.external.jpa")
public class ExternalJpaProperty extends JpaProperty {

}
