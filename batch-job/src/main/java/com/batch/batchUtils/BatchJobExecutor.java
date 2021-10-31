package com.batch.batchUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

import static com.batch.batchUtils.JobUtils.*;


@Slf4j
@RequiredArgsConstructor
@DisallowConcurrentExecution
public class BatchJobExecutor implements Job {

    private final JobLocator jobLocator;
    private final JobLauncher jobLauncher;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String jobName = getQuartzJobName(context.getMergedJobDataMap());
            log.info("[{}] started.", jobName);
            JobParameters jobParameters = getQuartzJobParameters(context);
            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
            log.info("[{}] completed.", jobName);
        } catch (NoSuchJobException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | SchedulerException e) {
            log.error("job execution exception! - {}", e.getCause());
            throw new JobExecutionException();
        }
    }
}
