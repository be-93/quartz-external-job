package com.core.db.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.core.repository"}
        , entityManagerFactoryRef = "coreEntityManagerFactory"
        , transactionManagerRef = "coreTransactionManager"
)
public class CoreDataSourceConfiguration {

    @Bean(name = "coreDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.core")
    @Qualifier("coreEntityManagerFactory")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "coreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("coreDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.core.model")
                .persistenceUnit("core")
                .build();
    }

    @Bean(name = "coreTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("coreEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
