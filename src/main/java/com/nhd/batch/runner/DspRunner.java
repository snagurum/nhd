package com.nhd.batch.runner;

import com.nhd.models.JobStatus;
import com.nhd.models.LoadDspTickers;
import com.nhd.models.Stock;
import com.nhd.util.Constants;
import com.nhd.models.HttpResponse;
import com.nhd.service.StockService;

import com.nhd.util.JobName;
import com.nhd.util.http.CookieHandler;
import com.nhd.util.http.Http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DspRunner {

    private static final Logger log = LoggerFactory.getLogger(DspRunner.class);

    @Autowired
    private StockService stockService ;

    private Integer totalRounds = 5;

    public void homePage(Stock ticker, CookieHandler cookies) throws IOException{
        log.info(" ====>>>> homePage");
        Http.loadPage(Constants.NSE_HOME_URL, cookies);
    }

    public void tickerPage(Stock ticker, CookieHandler cookies) throws IOException{
        log.info(" ====>>>> tickerPage");
        Http.loadPage(
            Constants.NSE_HOME_URL+"/get-quotes/equity?symbol=" + ticker.getTicker()
            , cookies);
    }

    public HttpResponse tickerDetails(Stock ticker, CookieHandler cookies) throws IOException{
        log.info(" ====>>>> tickerDetails");
        return Http.loadPage(
            Constants.NSE_HOME_URL+"/api/quote-equity?symbol=" + ticker.getTicker()
            , cookies);
    }

    public HttpResponse tickerTradeDetails(Stock ticker, CookieHandler cookies) throws IOException{
        log.info(" ====>>>> tickerTradeDetails");
        return Http.loadPage(
            Constants.NSE_HOME_URL+"/api/quote-equity?symbol="+ ticker.getTicker() + "&section=trade_info"
            , cookies);
    }

    public LoadDspTickers getDspTickerInfo(Stock ticker){
        try{
            CookieHandler cookies = new CookieHandler() ;
            homePage(ticker, cookies);
            tickerPage(ticker, cookies);
            String companyDetails = tickerDetails(ticker, cookies).getResponseBody();
            String tradeDetails = tickerTradeDetails(ticker, cookies).getResponseBody();
            LoadDspTickers loadDspTickers = new LoadDspTickers();
            loadDspTickers.setTicker(ticker.getTicker());
            loadDspTickers.setCompanyDetails(companyDetails);
            loadDspTickers.setTradeDetails(tradeDetails);
            return loadDspTickers;
        }catch(Exception e){
            log.error(ticker.getTicker(),e.getMessage(), e);
            return null;
        }

    }

    @Scheduled(cron="#{${loader.dsp_ticker.scheduler.cron}}")
    public void runJob(){
        List<JobStatus> jobs = stockService.getTodaysJobStatusByJobName(String.valueOf(JobName.DSP_TICKER));
        if(!jobs.isEmpty()) {
            log.info("Job {} has already been started ....",jobs.get(0));
            return;
        }

        JobStatus audit = stockService.startJob(JobName.DSP_TICKER);
        List<Stock> remainingTickers = stockService.getActiveTickers();
        Map<String, LoadDspTickers> processedTickers = new HashMap<>();

        int rounds = 0;
        boolean allFailed = false;
        while( rounds < totalRounds && !allFailed && !remainingTickers.isEmpty()) {
            log.info( "DSP tickers loader: Round = {}, Remaining = {},  Retrieved = {}", rounds,remainingTickers.size(), processedTickers.keySet().size());
            AtomicInteger failureCount = new AtomicInteger();
            remainingTickers.stream().parallel().forEach(item -> {
                LoadDspTickers dspTicker = getDspTickerInfo(item);
                if (dspTicker != null)
                    processedTickers.put(item.getTicker(), dspTicker);
                else {
                    failureCount.getAndIncrement();
                }
            });

            remainingTickers.removeAll(processedTickers.keySet());
            log.info( "DSP tickers Currently Retrieved count = {}", processedTickers.keySet().size());
            rounds++;
        }

        stockService.saveAllLoadDspTickers(processedTickers.values().stream().toList());
        audit.setSuccessCount(processedTickers.values().size());
        audit.setFailureCount(remainingTickers.size());
        stockService.endJob(audit);
        log.info(" DspTickers retrieved = {}, time = {}", processedTickers.keySet().size(),audit.getDuration());
    }


}
