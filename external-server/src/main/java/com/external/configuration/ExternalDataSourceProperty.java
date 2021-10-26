package com.external.configuration;

import com.core.db.DataSourceProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "spring.datasource.external.hikari")
//@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Validated
public class ExternalDataSourceProperty extends DataSourceProperty {

}
