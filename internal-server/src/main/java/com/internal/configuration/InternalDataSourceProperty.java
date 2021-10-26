package com.internal.configuration;

import com.core.db.DataSourceProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "spring.datasource.internal.hikari")
//@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Validated
public class InternalDataSourceProperty extends DataSourceProperty {

}
