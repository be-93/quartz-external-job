package com.internal.configuration;

import com.core.db.DataSourceFactory;
import com.internal.yml.InternalDataSourceProperty;
import com.internal.yml.InternalJpaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
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
@EnableJpaRepositories(basePackages = {"com.internal.repository"}
        , entityManagerFactoryRef = "internalEntityManagerFactory"
        , transactionManagerRef = "internalTransactionManager"
)
public class InternalDataSourceConfiguration extends DataSourceFactory {

    final private InternalDataSourceProperty internalDataSourceProperty;
    final private InternalJpaProperty internalJpaProperty;

    @Primary
    @Bean(name = "internalDataSourceProperties")
    public DataSourceProperties getDataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(internalDataSourceProperty.getDriverClassName());
        dataSourceProperties.setUrl(internalDataSourceProperty.getJdbcUrl());
        dataSourceProperties.setUsername(internalDataSourceProperty.getUsername());
        dataSourceProperties.setPassword(internalDataSourceProperty.getPassword());
        log.info("[internalDataSourceProperties] driver-class-name : {} , url : {}, username : {}",
                internalDataSourceProperty.getDriverClassName(), internalDataSourceProperty.getJdbcUrl(), internalDataSourceProperty.getUsername());
        return dataSourceProperties;
    }

    @Primary
    @Bean(name = "internalDataSource")
    public DataSource dataSource(@Qualifier("internalDataSourceProperties") DataSourceProperties properties) {
        return this.createDataSource(properties);
    }

    @Primary
    @Bean(name = "internalEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = getHibernateJpaVendorAdapter();
        JpaProperties jpaProperties = getJpaProperties();
        return this.createEntityManagerFactoryBuilder(hibernateJpaVendorAdapter, jpaProperties.getProperties());
    }

    @Primary
    @Bean(name = "internalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("internalEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder
            , @Qualifier("internalDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", internalJpaProperty.getDdlAuto());
        return this.createEntityManagerFactory(builder, dataSource,
                internalJpaProperty.getEntityPath(), internalJpaProperty.getPersistenceUnitName(), properties);
    }

    @Primary
    @Bean(name = "internalTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("internalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return this.createTransactionManager(entityManagerFactory);
    }

    private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(internalJpaProperty.isShowSql());
        hibernateJpaVendorAdapter.setDatabasePlatform(internalJpaProperty.getDatabasePlatform());
        return hibernateJpaVendorAdapter;
    }

    private JpaProperties getJpaProperties() {
        HashMap<String, String> map = new HashMap<>();
        map.put("hibernate.format_sql", String.valueOf(internalJpaProperty.isFormatSql()));
        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setDatabasePlatform(internalJpaProperty.getDatabasePlatform());
        jpaProperties.setShowSql(internalJpaProperty.isShowSql());
        jpaProperties.setProperties(map);
        return jpaProperties;
    }

}
