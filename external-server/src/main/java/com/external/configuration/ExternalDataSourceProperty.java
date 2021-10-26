package com.external.configuration;

import com.core.yml.DataSourceProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "spring.datasource.external.hikari")
public class ExternalDataSourceProperty extends DataSourceProperty {

}
