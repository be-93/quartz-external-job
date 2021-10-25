package com.core.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
public class DataSourceFactory {

    public DataSourceProperties dataSourceProperties(DataSourceProperty properties) {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(properties.getDriverClassName());
        dataSourceProperties.setUrl(properties.getUrl());
        dataSourceProperties.setName(properties.getUsername());
        dataSourceProperties.setPassword(properties.getPassword());
        log.info("driver-class-name {} , url {}, username {}"
                , properties.getDriverClassName(), properties.getUrl(), properties.getUsername());
        return dataSourceProperties;
    }

    public DataSource dataSource(DataSourceProperty properties) {
        LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy = new LazyConnectionDataSourceProxy(this.dataSourceProperties(properties).initializeDataSourceBuilder().build());
        return lazyConnectionDataSourceProxy;
    }

    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSourceProperty properties, String entityPath) {
        return builder
                .dataSource(this.dataSource(properties))
                .packages(entityPath)
                .persistenceUnit("other")
                .build();
    }

    public PlatformTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
