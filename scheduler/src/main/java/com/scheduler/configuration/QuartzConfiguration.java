package com.scheduler.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerFactory;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

import static com.internal.configuration.InternalDataSourceConfiguration.INTERNAL_DATASOURCE;

@Slf4j
@Configuration
public class QuartzConfiguration {

	/**
	 * Quartz DataSource 설정
	 * Default: internalDataSource 으로 설정
	 * 스케줄을 다른 디비에서 설정할 경우 DataSource 생성 하여 주입이 필요함.
	 */
	private final DataSource dataSource;
	private final QuartzProperties quartzProperties;

	public QuartzConfiguration(@Qualifier(INTERNAL_DATASOURCE) DataSource dataSource, QuartzProperties quartzProperties) {
		this.dataSource = dataSource;
		this.quartzProperties = quartzProperties;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		schedulerFactoryBean.setJobFactory(jobFactory);

		schedulerFactoryBean.setApplicationContext(applicationContext);

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());
		schedulerFactoryBean.setOverwriteExistingJobs(true);
		schedulerFactoryBean.setDataSource(dataSource);
		schedulerFactoryBean.setQuartzProperties(properties);
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
		return schedulerFactoryBean;
	}
}
