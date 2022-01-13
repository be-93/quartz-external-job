package com.external.configuration;

import com.core.db.EntityManagerCreator;
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
    public static final String EXTERNAL_PERSISTENCE_UNIT = "external";

    private final JpaProperties properties;
    private final HibernateProperties hibernateProperties;
    private final ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders;
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    @Bean(name = EXTERNAL_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean externalEntityManagerFactory(@Qualifier(EXTERNAL_DATASOURCE) DataSource dataSource) {
        return new EntityManagerCreator(properties, hibernateProperties, metadataProviders, entityManagerFactoryBuilder, dataSource)
                .entityManagerFactory(EXTERNAL_PACKAGE, EXTERNAL_PERSISTENCE_UNIT);
    }

    @Bean(name = EXTERNAL_TRANSACTION_MANAGER)
    public PlatformTransactionManager externalTransactionManager(@Qualifier(EXTERNAL_DATASOURCE) DataSource dataSource,
            @Qualifier(EXTERNAL_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
