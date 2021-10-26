package com.external.configuration;

import com.core.db.DataSourceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"com.external.repository"}
        , entityManagerFactoryRef = "externalEntityManagerFactory"
        , transactionManagerRef = "externalTransactionManager"
)
public class ExternalDataSourceConfiguration extends DataSourceFactory {

    final private ExternalDataSourceProperty externalDataSourceProperty;

    @Bean(name = "externalDataSourceProperties")
    public DataSourceProperties getDataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(externalDataSourceProperty.getDriverClassName());
        dataSourceProperties.setUrl(externalDataSourceProperty.getJdbcUrl());
        dataSourceProperties.setUsername(externalDataSourceProperty.getUsername());
        dataSourceProperties.setPassword(externalDataSourceProperty.getPassword());
        log.info("[externalDataSourceProperties] driver-class-name : {} , url : {}, username : {}",
                externalDataSourceProperty.getDriverClassName(), externalDataSourceProperty.getJdbcUrl(), externalDataSourceProperty.getUsername());
        return dataSourceProperties;
    }

    @Bean(name = "externalDataSource")
    public DataSource dataSource(@Qualifier("externalDataSourceProperties") DataSourceProperties properties) {
        return this.generateDataSource(properties);
    }

    @Bean(name = "externalEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

    @Bean(name = "externalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("externalEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder
            , @Qualifier("externalDataSource") DataSource dataSource) {
        return this.generateEntityManagerFactory(builder, dataSource, "com.external.entity", "external");
    }

    @Bean(name = "externalTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("externalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return this.generateTransactionManager(entityManagerFactory);
    }

}
