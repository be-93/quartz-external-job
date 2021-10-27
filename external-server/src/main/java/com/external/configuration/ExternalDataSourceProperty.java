package com.external.configuration;

import com.core.yml.DataSourceProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.external.hikari")
public class ExternalDataSourceProperty extends DataSourceProperty {

}