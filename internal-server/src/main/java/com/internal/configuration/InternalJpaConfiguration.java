package com.internal.configuration;

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
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

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
    public static final String INTERNAL_TRANSACTION_MANAGER = "internalTransactionManager";
    public static final String INTERNAL_PERSISTENCE_UNIT = "internal";

    private final JpaProperties properties;
    private final HibernateProperties hibernateProperties;
    private final ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders;
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    @Primary
    @Bean(name = INTERNAL_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean internalEntityManagerFactory(@Qualifier(INTERNAL_DATASOURCE) DataSource dataSource) {
        return new EntityManagerCreator(properties, hibernateProperties, metadataProviders, entityManagerFactoryBuilder, dataSource)
                .entityManagerFactory(INTERNAL_PACKAGE, INTERNAL_PERSISTENCE_UNIT);
    }

//    @Bean(name = INTERNAL_TRANSACTION_MANAGER)
//    public PlatformTransactionManager internalTransactionManager(@Qualifier(INTERNAL_DATASOURCE) DataSource dataSource,
//            @Qualifier(INTERNAL_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setDataSource(dataSource);
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }

}
