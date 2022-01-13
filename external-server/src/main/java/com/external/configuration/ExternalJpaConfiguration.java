package com.external.configuration;

import com.core.db.DataSourceFactory;
import com.core.db.EntityManagerCreator;
import com.external.yml.ExternalHibernateProperties;
import com.external.yml.ExternalJpaProperty;
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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collection;

import static com.external.configuration.ExternalDataSourceConfiguration.EXTERNAL_DATASOURCE;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class ExternalJpaConfiguration {

    public static final String EXTERNAL_PACKAGE = "com.external.domain";
    public static final String EXTERNAL_ENTITY_MANAGER_FACTORY = "externalEntityManagerFactory";
    public static final String EXTERNAL_TRANSACTION_MANAGER = "externalTransactionManager";
    public static final String EXTERNAL_TRANSACTION_MANAGER_FACTORY_BUILDER = "externalEntityManagerFactoryBuilder";
    public static final String EXTERNAL_PERSISTENCE_UNIT = "external";

    private final ExternalJpaProperty externalJpaProperty;
    private final ExternalHibernateProperties externalHibernateProperties;
    private final ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders;

    @Bean(name = EXTERNAL_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean externalEntityManagerFactory(@Qualifier(EXTERNAL_DATASOURCE) DataSource dataSource,
                                                                               @Qualifier(EXTERNAL_TRANSACTION_MANAGER_FACTORY_BUILDER) EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return new EntityManagerCreator(externalJpaProperty, externalHibernateProperties, metadataProviders, entityManagerFactoryBuilder, dataSource)
                .entityManagerFactory(EXTERNAL_PACKAGE, EXTERNAL_PERSISTENCE_UNIT);
    }

    @Bean(name = EXTERNAL_TRANSACTION_MANAGER)
    public PlatformTransactionManager externalTransactionManager(@Qualifier(EXTERNAL_DATASOURCE) DataSource dataSource, @Qualifier(EXTERNAL_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(EXTERNAL_TRANSACTION_MANAGER_FACTORY_BUILDER)
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        return DataSourceFactory.createEntityManagerFactoryBuilder(hibernateJpaVendorAdapter, externalJpaProperty.getProperties());
    }

}
