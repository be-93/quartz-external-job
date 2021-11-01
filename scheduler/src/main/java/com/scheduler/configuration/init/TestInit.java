package com.scheduler.configuration.init;

import com.batch.batchUtils.BatchJobExecutor;
import com.scheduler.quartzUtils.JobRequest;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import static com.scheduler.quartzUtils.JobUtils.createJob;
import static com.scheduler.quartzUtils.JobUtils.createTrigger;

@Component
@RequiredArgsConstructor
public class TestInit implements ApplicationListener<ContextRefreshedEvent> {

    private final ApplicationContext context;

    private final SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("job", "TestJob-job1");
            JobRequest jobRequest = JobRequest
                    .builder()
                    .cronExpression("*/5 * * * * ?")
                    .jobName("job1")
                    .jobDataMap(jobDataMap)
                    .build();

            JobDetail job1 = createJob(jobRequest, BatchJobExecutor.class, context);
            Trigger trigger1 = createTrigger(jobRequest);

            JobDetail jobDetail = schedulerFactoryBean.getScheduler().getJobDetail(job1.getKey());
            if (jobDetail != null) {
                schedulerFactoryBean.getScheduler().deleteJob(jobDetail.getKey());
            }
            schedulerFactoryBean.getScheduler().scheduleJob(job1, trigger1);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
