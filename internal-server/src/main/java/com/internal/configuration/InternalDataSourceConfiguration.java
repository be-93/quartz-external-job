package com.internal.configuration;

import com.internal.yml.InternalDataSourceProperty;
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
public class InternalDataSourceConfiguration {

    public static final String INTERNAL_DATASOURCE_PREFIX = "spring.internal.datasource";
    public static final String INTERNAL_DATASOURCE_PROPERTIES = "internalDataSourceProperties";
    public static final String INTERNAL_DATASOURCE = "internalDataSource";

    private final InternalDataSourceProperty internalDataSourceProperty;

    @Bean(name = INTERNAL_DATASOURCE_PROPERTIES)
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(internalDataSourceProperty.getDriverClassName());
        dataSourceProperties.setUrl(internalDataSourceProperty.getJdbcUrl());
        dataSourceProperties.setUsername(internalDataSourceProperty.getUsername());
        dataSourceProperties.setPassword(internalDataSourceProperty.getPassword());
        return dataSourceProperties;
    }

    @Bean(name = INTERNAL_DATASOURCE)
    @ConfigurationProperties(prefix = INTERNAL_DATASOURCE_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

}
