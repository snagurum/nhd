package com.nhd.service;

import java.util.List;

import com.nhd.models.LoadDspTickers;
import com.nhd.models.LoadTickers;
import com.nhd.models.Stock;
import com.nhd.models.JobStatus;
import com.nhd.util.JobName;


public interface StockService{

        List<Stock> getActiveTickers();

        void saveAllLoadTickers(List<LoadTickers> tickers);

        void saveAllLoadDspTickers(List<LoadDspTickers> tickers);

        JobStatus startJob(JobName name);

        void endJob(JobStatus job);

        List<JobStatus> getTodaysJobStatus();

        List<JobStatus> getTodaysJobStatusByJobName(String jobName);
}