package com.scheduler.init;

import com.scheduler.configuration.quartz.RemoteClassLoader;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitRunner implements CommandLineRunner {

    private final Scheduler scheduler;
    private final RemoteClassLoader remoteClassLoader;

    @Override
    public void run(String... args) throws Exception {
        JobKey jobKey = JobKey.jobKey("job1", null);
        JobDetail jobDetail = buildJobDetail(jobKey);
        Trigger trigger = buildJobTrigger(jobKey);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobDetail buildJobDetail(JobKey jobKey) throws ClassNotFoundException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("key1", "value1");
        jobDataMap.put("key2", 2);

        return JobBuilder.newJob(remoteClassLoader.loadClass("com.batch.batchUtils.BatchJobExecutor", Job.class))
                .withIdentity(jobKey)
                .withDescription("Simple Quartz Job Detail")
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger buildJobTrigger(JobKey jobKey) {
        return TriggerBuilder.newTrigger()
                .forJob(jobKey)
                .withDescription("Simple Quartz Job Trigger")
                .startNow()
                .build();
    }
}
