package com.batchUtils;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.*;

import static com.core.utils.NullSafetyUtils.isEmpty;
import static com.core.utils.NullSafetyUtils.nullSafetyMap;

public class JobUtils {

    private static final String JOB_NAME_KEY = "job";
    private static final String JOB_PARAMETERS_NAME_KEY_BY_CONFIG = "jobParameters";
    private static final String JOB_PARAMETERS_NAME_KEY_BY_TRIGGER = "triggerJobParameters";
    private static final String JOB_PARAMETERS_INSTANCE_ID_KEY = "InstanceId";
    private static final String JOB_PARAMETERS_TIMESTAMP_KEY = "timestamp";
    private static final String JOB_DEFAULT_CRON_EXPRESSION = "0 0 0/3 1/1 * ? *";
    private static final List<String> KEYWORDS = Arrays.asList(JOB_NAME_KEY, JOB_PARAMETERS_NAME_KEY_BY_CONFIG);

    public static JobDetailFactoryBeanBuilder jobDetailFactoryBeanBuilder() {
        return new JobDetailFactoryBeanBuilder();
    }

    public static CronTriggerFactoryBeanBuilder cronTriggerFactoryBeanBuilder() {
        return new CronTriggerFactoryBeanBuilder();
    }

    public static String getQuartzJobName(JobDataMap jobDataMap){
        return (String) jobDataMap.get(JOB_NAME_KEY);
    }

    public static JobParameters getQuartzJobParameters(JobExecutionContext context) throws SchedulerException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        return new JobParametersBuilder(
                getMergedJobParameters(
                        (JobParameters) jobDataMap.get(JOB_PARAMETERS_NAME_KEY_BY_CONFIG),
                        (JobParameters) jobDataMap.get(JOB_PARAMETERS_NAME_KEY_BY_TRIGGER)
                )
        )
                .addString(JOB_PARAMETERS_INSTANCE_ID_KEY, context.getScheduler().getSchedulerInstanceId())
                .addLong(JOB_PARAMETERS_TIMESTAMP_KEY, System.currentTimeMillis())
                .toJobParameters();
    }

    private static JobParameters getMergedJobParameters(JobParameters jobParameters1, JobParameters jobParameters2) {
        Map<String, JobParameter> merged = new HashMap<>();
        merged.putAll(nullSafetyMap(jobParameters1.getParameters()));
        merged.putAll(nullSafetyMap(jobParameters2.getParameters()));
        return new JobParameters(merged);
    }

    public static class JobDetailFactoryBeanBuilder {

        boolean durability = true;
        boolean requestsRecovery = true;
        private Map<String, Object> map;
        private JobParametersBuilder jobParametersBuilder;

        JobDetailFactoryBeanBuilder() {
            this.map = new HashMap<>();
            this.jobParametersBuilder = new JobParametersBuilder();
        }

        public JobDetailFactoryBeanBuilder job(Job job) {
            this.map.put(JOB_NAME_KEY, job.getName());
            return this;
        }

        public JobDetailFactoryBeanBuilder durability(boolean durability){
            this.durability = durability;
            return this;
        }

        public JobDetailFactoryBeanBuilder requestsRecovery(boolean requestsRecovery){
            this.requestsRecovery = requestsRecovery;
            return this;
        }

        public JobDetailFactoryBeanBuilder parameter(String key, Object value){
            if(KEYWORDS.contains(key)){
                throw new RuntimeException("Invalid Parameter.");
            }
            this.addParameter(key, value);
            return this;
        }

        private void addParameter(String key, Object value) {
            if (value instanceof String) {
                this.jobParametersBuilder.addString(key, (String) value);
                return;
            } else if (value instanceof Float || value instanceof Double) {
                this.jobParametersBuilder.addDouble(key, ((Number) value).doubleValue());
                return;
            } else if (value instanceof Integer || value instanceof Long) {
                this.jobParametersBuilder.addLong(key, ((Number) value).longValue());
                return;
            } else if (value instanceof Date) {
                this.jobParametersBuilder.addDate(key, (Date) value);
                return;
            } else if (value instanceof JobParameter) {
                this.jobParametersBuilder.addParameter(key, (JobParameter) value);
                return;
            }
            throw new RuntimeException("Not Supported Parameter Type.");
        }

        public JobDetailFactoryBean build(){
            if(!map.containsKey(JOB_NAME_KEY)) {
                throw new RuntimeException("Not Found Job Name.");
            }
            map.put(JOB_PARAMETERS_NAME_KEY_BY_CONFIG, jobParametersBuilder.toJobParameters());

            JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
            jobDetailFactory.setJobClass(BatchJobExecutor.class);
            jobDetailFactory.setDurability(this.durability);
            jobDetailFactory.setRequestsRecovery(this.requestsRecovery);
            jobDetailFactory.setJobDataAsMap(this.map);
            return jobDetailFactory;
        }
    }

    public static class CronTriggerFactoryBeanBuilder {
        private String name;
        private String cronExpression;
        private JobDetailFactoryBean jobDetailFactoryBean;

        public CronTriggerFactoryBeanBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CronTriggerFactoryBeanBuilder cronExpression(String cronExpression) {
            if (isEmpty(cronExpression)) {
                cronExpression = JOB_DEFAULT_CRON_EXPRESSION;
            }
            this.cronExpression = cronExpression;
            return this;
        }

        public CronTriggerFactoryBeanBuilder jobDetailFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
            this.jobDetailFactoryBean = jobDetailFactoryBean;
            return this;
        }

        public CronTriggerFactoryBean build() {
            if(this.cronExpression == null || this.jobDetailFactoryBean == null){
                throw new RuntimeException("cron 표현식이 존재하지 않습니다.");
            }
            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setName(this.name);
            cronTriggerFactoryBean.setJobDetail(this.jobDetailFactoryBean.getObject());
            cronTriggerFactoryBean.setCronExpression(this.cronExpression);
            return cronTriggerFactoryBean;
        }
    }

}
