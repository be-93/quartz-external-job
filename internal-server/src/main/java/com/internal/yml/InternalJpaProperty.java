package com.internal.yml;

import com.core.yml.JpaProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.internal.jpa")
public class InternalJpaProperty extends JpaProperty {

}
