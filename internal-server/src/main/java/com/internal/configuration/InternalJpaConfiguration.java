package com.internal.configuration;

import com.core.db.DataSourceFactory;
import com.core.db.EntityManagerCreator;
import com.internal.yml.InternalHibernateProperties;
import com.internal.yml.InternalJpaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collection;

import static com.internal.configuration.InternalDataSourceConfiguration.INTERNAL_DATASOURCE;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class InternalJpaConfiguration {
    
    public static final String INTERNAL_PACKAGE = "com.internal.domain";
    public static final String INTERNAL_ENTITY_MANAGER_FACTORY = "internalEntityManagerFactory";
    public static final String INTERNAL_ENTITY_MANAGER_FACTORY_BUILDER = "internalEntityManagerFactoryBuilder";
    public static final String INTERNAL_TRANSACTION_MANAGER = "internalTransactionManager";
    public static final String INTERNAL_PERSISTENCE_UNIT = "internal";

    private final InternalJpaProperty internalJpaProperty;
    private final InternalHibernateProperties internalHibernateProperties;
    private final ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders;

    @Primary
    @Bean(name = INTERNAL_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean internalEntityManagerFactory(@Qualifier(INTERNAL_DATASOURCE) DataSource dataSource,
                                                                               @Qualifier(INTERNAL_ENTITY_MANAGER_FACTORY_BUILDER) EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return new EntityManagerCreator(internalJpaProperty, internalHibernateProperties, metadataProviders, entityManagerFactoryBuilder, dataSource)
                .entityManagerFactory(INTERNAL_PACKAGE, INTERNAL_PERSISTENCE_UNIT);
    }

    @Primary
    @Bean(INTERNAL_ENTITY_MANAGER_FACTORY_BUILDER)
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        return DataSourceFactory.createEntityManagerFactoryBuilder(hibernateJpaVendorAdapter, internalJpaProperty.getProperties());
    }

    @Primary
    @Bean(name = INTERNAL_TRANSACTION_MANAGER)
    public PlatformTransactionManager internalTransactionManager(@Qualifier(INTERNAL_DATASOURCE) DataSource dataSource,
                                                                 @Qualifier(INTERNAL_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
