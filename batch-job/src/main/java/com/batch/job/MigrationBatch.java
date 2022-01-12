package com.batch.job;

import com.external.domain.ExternalTest;
import com.internal.domain.InternalTest;
import com.internal.domain.InternalTestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Configuration
@EnableBatchProcessing
public class MigrationBatch {
    public static final String JOB_NAME = "MigrationJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final LocalContainerEntityManagerFactoryBean externalEntityManagerFactory;
    private final LocalContainerEntityManagerFactoryBean internalEntityManagerFactory;
    private final PlatformTransactionManager internalTransactionManager;
    private final InternalTestRepository internalTestRepository;

    private final int chunkSize = 10;

    public MigrationBatch(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                          @Qualifier("externalEntityManagerFactory") LocalContainerEntityManagerFactoryBean externalEntityManagerFactory,
                          @Qualifier("internalEntityManagerFactory") LocalContainerEntityManagerFactoryBean internalEntityManagerFactory,
                          @Qualifier("internalTransactionManager") PlatformTransactionManager internalTransactionManager,
                          InternalTestRepository internalTestRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.externalEntityManagerFactory = externalEntityManagerFactory;
        this.internalEntityManagerFactory = internalEntityManagerFactory;
        this.internalTransactionManager = internalTransactionManager;
        this.internalTestRepository = internalTestRepository;
    }

    @Bean
    @Transactional
    public Job MigrationJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(pagingStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step pagingStep() {
        return stepBuilderFactory.get(JOB_NAME + "_pagingStep")
                .transactionManager(internalTransactionManager)
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
                .queryString("select u from ExternalTest u")
                .pageSize(chunkSize)
                .entityManagerFactory(externalEntityManagerFactory.getObject())
                .name("pagingReader")
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
    public JpaItemWriter<InternalTest> writer() {
        JpaItemWriter<InternalTest> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(internalEntityManagerFactory.getObject());
        return writer;
    }

}