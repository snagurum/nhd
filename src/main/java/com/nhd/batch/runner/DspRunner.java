package com.nhd.batch.runner;

import com.nhd.models.LoadDspTickers;
import com.nhd.util.Constants;
import com.nhd.util.CookieHandler;
import com.nhd.util.Http;
import com.nhd.models.HttpResponse;
import com.nhd.service.StockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void homePage(String ticker, CookieHandler cookies) throws IOException{
        Http.loadPage(Constants.NSE_HOME_URL, cookies, true);
    }

    public void tickerPage(String ticker, CookieHandler cookies) throws IOException{
        Http.loadPage(
            Constants.NSE_HOME_URL+"/get-quotes/equity?symbol=" + ticker
            , cookies, true);
    }

    public HttpResponse tickerDetails(String ticker, CookieHandler cookies) throws IOException{
        return Http.loadPage(
            Constants.NSE_HOME_URL+"/api/quote-equity?symbol=" + ticker
            , cookies, true);
    }

    public HttpResponse tickerTradeDetails(String ticker, CookieHandler cookies) throws IOException{
        return Http.loadPage(
            Constants.NSE_HOME_URL+"/api/quote-equity?symbol="+ ticker + "&section=trade_info"
            , cookies, true);
    }

    public LoadDspTickers getDspTickerInfo(String ticker){
        try{
            CookieHandler cookies = new CookieHandler() ;
            homePage(ticker, cookies);
            tickerPage(ticker, cookies);
            String companyDetails = tickerDetails(ticker, cookies).getResponseBody();
            String tradeDetails = tickerTradeDetails(ticker, cookies).getResponseBody();
            LoadDspTickers loadDspTickers = new LoadDspTickers();
            loadDspTickers.setTicker(ticker);
            loadDspTickers.setCompanyDetails(companyDetails);
            loadDspTickers.setTradeDetails(tradeDetails);
            return loadDspTickers;
        }catch(Exception e){
            log.error(ticker,e.getMessage(), e);
            return null;
        }

    }

    public void run(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        List<String> dspTickers = stockService.getActiveTickers().subList(0,10);
        List<String> dspTickers = stockService.getActiveTickers();
        Map<String, LoadDspTickers> dspTickerMap = new HashMap<>();

        int rounds = 0;
        boolean allFailed = false;
        while( rounds < totalRounds && !allFailed && !dspTickers.isEmpty()) {
            Integer dspTickersListSize = dspTickers.size();
            Integer dspTickersMapSize = dspTickerMap.keySet().size();
            log.info( "DSP tickers Remaining count = {}", dspTickersListSize);
            log.info( "DSP tickers Retrieved count = {}", dspTickersMapSize);
            log.info("Round = {}", rounds);
            AtomicInteger failureCount = new AtomicInteger();
            dspTickers.stream().parallel().forEach(item -> {
                LoadDspTickers dspTicker = getDspTickerInfo(item);
                if (dspTicker != null)
                    dspTickerMap.put(item, getDspTickerInfo(item));
                else {
                    failureCount.getAndIncrement();
                }
            });

            dspTickers.removeAll(dspTickerMap.keySet());
            log.info( "DSP tickers Currently Retrieved count = {}", dspTickerMap.keySet().size());

            rounds++;
        }

        log.trace(" DSP Tickers data : {}",dspTickerMap );
        stopWatch.stop();
        stockService.saveAllLoadDspTickers(dspTickerMap.values().stream().toList());
        log.info(" DspTickers retrieved = {}, time = {}", dspTickerMap.keySet().size(),stopWatch.getTotalTimeSeconds());
    }


}
