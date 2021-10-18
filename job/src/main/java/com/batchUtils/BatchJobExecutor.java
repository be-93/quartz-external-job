package com.batchUtils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class BatchJobExecutor implements Job {

    @Autowired
    private JobLocator jobLocator;

    @Autowired
    private JobLauncher jobLauncher;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String jobName = JobUtils.getQuartzJobName(context.getMergedJobDataMap());
            log.info("[{}] started.", jobName);
            JobParameters jobParameters = JobUtils.getQuartzJobParameters(context);
            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
            log.info("[{}] completed.", jobName);
        } catch (NoSuchJobException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | SchedulerException e) {
            log.error("job execution exception! - {}", e.getCause());
            throw new JobExecutionException();
        }
    }
}
