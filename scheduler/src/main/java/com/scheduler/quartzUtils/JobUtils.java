package com.scheduler.quartzUtils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.text.ParseException;

@Slf4j
public final class JobUtils {

    public static JobDetail createJob(JobRequest jobRequest, Class<? extends Job> jobClass, ApplicationContext context) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(false);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());

        if (jobRequest.getJobDataMap() != null) {
            factoryBean.setJobDataMap(jobRequest.getJobDataMap());
        }

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public static Trigger createTrigger(JobRequest jobRequest) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());
        factoryBean.setCronExpression(jobRequest.getCronExpression());
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return factoryBean.getObject();
    }

}
