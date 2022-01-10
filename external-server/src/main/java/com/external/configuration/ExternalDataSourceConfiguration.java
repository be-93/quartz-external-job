package com.external.configuration;

import com.core.db.DataSourceFactory;
import com.external.yml.ExternalDataSourceProperty;
import com.external.yml.ExternalJpaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
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
    final private ExternalJpaProperty externalJpaProperty;

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
        return this.createDataSource(properties);
    }

    @Bean(name = "externalEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = getHibernateJpaVendorAdapter();
        JpaProperties jpaProperties = getJpaProperties();
        return this.createEntityManagerFactoryBuilder(hibernateJpaVendorAdapter, jpaProperties.getProperties());
    }

    @Bean(name = "externalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("externalEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder
            , @Qualifier("externalDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", externalJpaProperty.getDdlAuto());
        return this.createEntityManagerFactory(builder, dataSource,
                externalJpaProperty.getEntityPath(), externalJpaProperty.getPersistenceUnitName(), properties);
    }

    @Bean(name = "externalTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("externalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return this.createTransactionManager(entityManagerFactory);
    }

    private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(externalJpaProperty.isShowSql());
        hibernateJpaVendorAdapter.setDatabasePlatform(externalJpaProperty.getDatabasePlatform());
        return hibernateJpaVendorAdapter;
    }

    private JpaProperties getJpaProperties() {
        HashMap<String, String> map = new HashMap<>();
        map.put("hibernate.format_sql", String.valueOf(externalJpaProperty.isFormatSql()));
        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setDatabasePlatform(externalJpaProperty.getDatabasePlatform());
        jpaProperties.setShowSql(externalJpaProperty.isShowSql());
        jpaProperties.setProperties(map);
        return jpaProperties;
    }

}
