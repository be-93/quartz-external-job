package com.internal.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties
public class InternalDataSourceConfiguration {

    public static final String INTERNAL_DATASOURCE_PREFIX = "spring.internal.datasource.hikari";
    public static final String INTERNAL_JPA_PREFIX = "spring.internal.datasource.jpa";
    public static final String INTERNAL_JPA_HIBERNATE_PREFIX = "spring.internal.datasource.jpa.hibernate";
    public static final String INTERNAL_DATASOURCE_PROPERTIES = "internalDataSourceProperties";
    public static final String INTERNAL_DATASOURCE = "internalDataSource";

    @Primary
    @Bean(name = INTERNAL_DATASOURCE)
    @ConfigurationProperties(prefix = INTERNAL_DATASOURCE_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

}
