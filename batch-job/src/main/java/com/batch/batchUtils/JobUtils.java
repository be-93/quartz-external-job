package com.batch.batchUtils;

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

    public static JobParameters getJobParameters(Map<String, Object> jobDataMap) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        for (Map.Entry<String, Object> entry : jobDataMap.entrySet()) {
            if (entry.getValue() instanceof String) {
                jobParametersBuilder.addString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Double) {
                jobParametersBuilder.addDouble(entry.getKey(), (Double) entry.getValue());
            } else if (entry.getValue() instanceof Long) {
                jobParametersBuilder.addString(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return jobParametersBuilder.toJobParameters();
    }

}
