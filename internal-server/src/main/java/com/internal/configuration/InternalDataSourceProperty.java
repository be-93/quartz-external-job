package com.internal.configuration;

import com.core.yml.DataSourceProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "spring.datasource.internal.hikari")
public class InternalDataSourceProperty extends DataSourceProperty {

}
