package com.nhd.service;

import java.sql.Timestamp;
import java.util.List;

import com.nhd.models.JobStatus;
import com.nhd.models.LoadBulkTickers;
import com.nhd.models.LoadDspTickers;
import com.nhd.service.repo.JobStatusRepository;
import com.nhd.service.repo.LoadBulkTickersRepository;
import com.nhd.service.repo.LoadDspTickersRepository;
import com.nhd.service.repo.LoadTickersRepository;
import com.nhd.service.repo.StockRepository;
import com.nhd.models.LoadTickers;
import com.nhd.models.Stock;
import com.nhd.util.JobName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockService.class);

    @Autowired
    LoadTickersRepository loadTickerRepo;

    @Autowired
    LoadDspTickersRepository loadDspRepo;

    @Autowired
    JobStatusRepository jobStatusRepo;

    @Autowired
    StockRepository stockRepo;

    @Autowired
    LoadBulkTickersRepository loadBulkTickersRepo;


    public List<Stock> getActiveTickers(){
        return stockRepo.getActiveTickers();
    }

    public List<Stock> noHistoryStocks(){
        return stockRepo.noHistoryStocks();
    }

    public Stock saveStock(Stock stock){
        return stockRepo.save(stock);
    }

    public void saveAllLoadTickers(List<LoadTickers> tickers){

        log.info("truncating load_tickers table");
        loadTickerRepo.truncateTable();
        log.info("populating load_tickers table {}", tickers.size());
        loadTickerRepo.saveAll(tickers);
        log.info("marking new tickers...");
        loadTickerRepo.markNewTickers();
        log.info("adding new tickers");
        loadTickerRepo.addNewStocks();
    }

    public void saveAllLoadDspTickers(List<LoadDspTickers> tickers) {
        loadDspRepo.saveAll(tickers);
    }

//-------------------------------- logging

    public JobStatus startJob(JobName jobName ) {
        return startJobWithComment(jobName,null);
    }

    public JobStatus startJobWithComment(JobName jobName,String comment){
        JobStatus job = new JobStatus();
        job.setType(jobName.getJobType());
        job.setName(jobName.toString());
        job.setComments(comment);
        job.setStartTime(new Timestamp(System.currentTimeMillis()));
        job.setStatus("Started");
        return jobStatusRepo.save(job);
    }

    public void endJob(JobStatus job) {
        job.setEndTime(new Timestamp(System.currentTimeMillis()));
        job.setStatus("Completed");
        job.setDuration( (job.getEndTime().getTime()-job.getStartTime().getTime())/1000);
        jobStatusRepo.save(job);
    }

    public List<JobStatus> getTodaysJobStatus(){
        return jobStatusRepo.getTodaysJobStatus();
    }

    public List<JobStatus> getTodaysJobStatusByJobName(String jobName) {
        return jobStatusRepo.getTodaysJobStatusByJobName(jobName);
    }

    public void saveAllLoadBulkTickers(List<LoadBulkTickers> tickers){
        loadBulkTickersRepo.saveAll(tickers);
    }
}