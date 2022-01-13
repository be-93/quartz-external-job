package com.external.configuration;

import com.external.yml.ExternalDataSourceProperty;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties
public class ExternalDataSourceConfiguration {

    public static final String EXTERNAL_DATASOURCE_PREFIX = "spring.external.datasource";
    public static final String EXTERNAL_DATASOURCE_PROPERTIES = "externalDataSourceProperties";
    public static final String EXTERNAL_DATASOURCE = "externalDataSource";

    private final ExternalDataSourceProperty externalDataSourceProperty;

    @Bean(name = EXTERNAL_DATASOURCE_PROPERTIES)
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(externalDataSourceProperty.getDriverClassName());
        dataSourceProperties.setUrl(externalDataSourceProperty.getJdbcUrl());
        dataSourceProperties.setUsername(externalDataSourceProperty.getUsername());
        dataSourceProperties.setPassword(externalDataSourceProperty.getPassword());
        return dataSourceProperties;
    }

    @Bean(name = EXTERNAL_DATASOURCE)
    @ConfigurationProperties(prefix = EXTERNAL_DATASOURCE_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

}
