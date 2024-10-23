package com.nhd.service;

import com.nhd.models.JobStatus;
import com.nhd.util.JobName;

import java.util.List;

public interface AuditService {


    JobStatus startJob(JobName name);

    JobStatus startJobWithComment(JobName name,String comment);

    void failJob(JobStatus job);

    void endJob(JobStatus job);

    void endJobWithSuccessFailureCount(JobStatus job,int success, int failure);

    List<JobStatus> getTodaysJobStatus();

    List<JobStatus> getTodaysJobStatusByJobName(String jobName);

}
