package com.batch.job;

import com.external.domain.ExternalTest;
import com.internal.domain.InternalTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static com.external.configuration.ExternalDataSourceConfiguration.EXTERNAL_DATASOURCE;
import static com.external.configuration.ExternalJpaConfiguration.EXTERNAL_ENTITY_MANAGER_FACTORY;
import static com.internal.configuration.InternalDataSourceConfiguration.INTERNAL_DATASOURCE;

@Slf4j
@Configuration
@EnableBatchProcessing
public class MigrationBatch {
    public static final String JOB_NAME = "MigrationJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource internalDataSource;
    private final DataSource externalDataSource;
    private final LocalContainerEntityManagerFactoryBean externalEntityManager;

    private final int chunkSize = 10000;

    public MigrationBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                          @Qualifier(INTERNAL_DATASOURCE) DataSource internalDataSource,
                          @Qualifier(EXTERNAL_DATASOURCE) DataSource externalDataSource,
                          @Qualifier(EXTERNAL_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean externalEntityManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.internalDataSource = internalDataSource;
        this.externalDataSource = externalDataSource;
        this.externalEntityManager = externalEntityManager;
    }

    @Bean
    @Transactional
    public Job MigrationJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(pagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step pagingStep() {
        return stepBuilderFactory.get(JOB_NAME + "_pagingStep")
                .<ExternalTest, InternalTest>chunk(chunkSize)
                .reader(pagingReader())
                .processor(pagingProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<ExternalTest> pagingReader() {
        return new JpaPagingItemReaderBuilder<ExternalTest>()
                .name(JOB_NAME + "_pagingReader")
                .entityManagerFactory(externalEntityManager.getObject())
                .pageSize(chunkSize)
                .queryString("select e from ExternalTest e")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<ExternalTest, InternalTest> pagingProcessor() {
        return item -> {
            return new InternalTest(item.getId(), item.getName());
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<InternalTest> writer() {
        JdbcBatchItemWriter<InternalTest> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(BeanPropertySqlParameterSource::new);
        writer.setDataSource(internalDataSource);
        writer.setSql("insert into internal_table(id, name) values (:id, :name) on conflict (id) do " +
                "update set name = :name");
        return writer;
    }

}