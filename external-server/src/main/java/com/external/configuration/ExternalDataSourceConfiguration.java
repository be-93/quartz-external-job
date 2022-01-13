package com.external.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
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

    public static final String EXTERNAL_DATASOURCE_PREFIX = "spring.external.datasource.hikari";
    public static final String EXTERNAL_JPA_PREFIX = "spring.external.datasource.jpa";
    public static final String EXTERNAL_JPA_HIBERNATE_PREFIX = "spring.external.datasource.jpa.hibernate";
    public static final String EXTERNAL_DATASOURCE = "externalDataSource";

    @Bean(name = EXTERNAL_DATASOURCE)
    @ConfigurationProperties(prefix = EXTERNAL_DATASOURCE_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

}
