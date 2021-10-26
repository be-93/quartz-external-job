package com.internal.configuration;

import com.core.db.DataSourceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
//@PropertySource(value = "classpath:application.yml")
@EnableJpaRepositories(basePackages = {"com.internal.repository"}
        , entityManagerFactoryRef = "internalEntityManagerFactory"
        , transactionManagerRef = "internalTransactionManager"
)
public class InternalDataSourceConfiguration extends DataSourceFactory {

    final private InternalDataSourceProperty internalDataSourceProperty;

    @Primary
    @Bean(name = "internalDataSourceProperties")
    public DataSourceProperties getDataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(internalDataSourceProperty.getDriverClassName());
        dataSourceProperties.setUrl(internalDataSourceProperty.getJdbcUrl());
        dataSourceProperties.setUsername(internalDataSourceProperty.getUsername());
        dataSourceProperties.setPassword(internalDataSourceProperty.getPassword());
        log.info("[internalDataSourceProperties] driver-class-name : {} , url : {}, username : {}"
                , internalDataSourceProperty.getDriverClassName(), internalDataSourceProperty.getJdbcUrl(), internalDataSourceProperty.getUsername());
        return dataSourceProperties;
    }

    @Primary
    @Bean(name = "internalDataSource")
    public DataSource dataSource(@Qualifier("internalDataSourceProperties") DataSourceProperties properties) {
        return this.getDataSource(properties);
    }

    @Primary
    @Bean(name = "internalEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

    @Primary
    @Bean(name = "internalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("internalEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder
            , @Qualifier("internalDataSource") DataSource dataSource) {
        return this.getEntityManagerFactory(builder, dataSource, "com.internal.entity", "internal");
    }

    @Primary
    @Bean(name = "internalTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("internalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return this.getTransactionManager(entityManagerFactory);
    }

}
