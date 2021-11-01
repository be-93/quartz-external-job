package com.scheduler.quartzUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.quartz.JobDataMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class JobRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateAt;

    private String jobGroup;
    private String jobName;
    private String cronExpression;
    private JobDataMap jobDataMap;

    @Builder
    public JobRequest(LocalDateTime startDateAt, String jobGroup, String jobName, String cronExpression, JobDataMap jobDataMap) {
        this.startDateAt = startDateAt;
        this.jobGroup = jobGroup;
        this.jobName = jobName;
        this.cronExpression = cronExpression;
        this.jobDataMap = jobDataMap;
    }
}

