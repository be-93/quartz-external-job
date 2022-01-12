package com.internal.yml;

import com.core.yml.DataSourceProperty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "spring.datasource.internal.hikari")
public class InternalDataSourceProperty extends DataSourceProperty {
    @Value("${spring.datasource.internal.hikari.data-source-properties.rewriteBatchedStatements}")
    private boolean rewriteBatchedStatements;
}
