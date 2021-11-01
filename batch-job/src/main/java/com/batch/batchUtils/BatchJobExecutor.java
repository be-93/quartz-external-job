package com.batch.batchUtils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static com.batch.batchUtils.JobUtils.*;


@Slf4j
//@RequiredArgsConstructor
//@DisallowConcurrentExecution 클러스터링 환경에서 적용되지 않음.
public class BatchJobExecutor implements org.quartz.Job {

    @Autowired
    private JobLocator jobLocator;
    @Autowired
    private JobLauncher jobLauncher;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobKey quartzJob = context.getJobDetail().getKey();
            Map<String, Object> quartzParameters = context.getJobDetail().getJobDataMap().getWrappedMap();
            quartzParameters.put("localDateTime", System.currentTimeMillis());
            Job job = (Job) BeanUtils.getBean(quartzJob.getName());
            JobParameters jobParameters = getJobParameters(quartzParameters);

            log.info("[{}] started.", quartzJob.getName());
            jobLauncher.run(job, jobParameters);
            log.info("[{}] completed.", quartzJob.getName());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("job execution exception! - {}", e.getCause());
            throw new JobExecutionException();
        }
    }
}
