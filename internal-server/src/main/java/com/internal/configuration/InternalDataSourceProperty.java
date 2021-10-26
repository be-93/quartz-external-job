package com.internal.configuration;

import com.core.yml.DataSourceProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.internal.hikari")
public class InternalDataSourceProperty extends DataSourceProperty {

}
