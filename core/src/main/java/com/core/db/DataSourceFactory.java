package com.core.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Slf4j
public class DataSourceFactory {

    public static DataSource createDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        dataSource.setDataSource(new LazyConnectionDataSourceProxy(properties
                                .initializeDataSourceBuilder()
                                .build()));
        return dataSource;
    }

    public static EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter, Map<String, String> hashMap) {
        return new EntityManagerFactoryBuilder(hibernateJpaVendorAdapter, hashMap, null);
    }
    
    public static LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource
            , String entityPath, String persistenceUnitName, Map<String, Object> properties) {
        return builder
                .dataSource(dataSource)
                .packages(entityPath)
                .persistenceUnit(persistenceUnitName)
                .properties(properties)
                .build();
    }

    public static PlatformTransactionManager createTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
