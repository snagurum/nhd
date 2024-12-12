package com.nhd.batch.runner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.nhd.models.HttpResponse;
import com.nhd.models.JobStatus;
import com.nhd.models.LoadBulkTickers;
import com.nhd.models.LoadDspTickers;
import com.nhd.models.LoadTickers;
import com.nhd.models.Stock;
import com.nhd.service.StockService;
import com.nhd.util.Constants;
import com.nhd.util.JobName;
import com.nhd.util.http.CookieHandler;
import com.nhd.util.http.Http;


@Component
public class BulkRunner {
    

    private static final Logger log = LoggerFactory.getLogger(BulkRunner.class);

    @Autowired
    private StockService stockService ;

    private static Map<String,String> additionalHeaders = new HashMap<>();
    
    private static String homePageUrl = Constants.NSE_HOME_URL;
    private static String tickerPageUrl = Constants.NSE_HOME_URL+"/get-quotes/equity?symbol=";
    private static String tickerDetailsPageUrl = Constants.NSE_HOME_URL+"/api/quote-equity?symbol=";
    private static String tickerHistoricalPriceDetailsUrl = Constants.NSE_HOME_URL+"/api/historical/cm/equity?symbol=__ticker__&series=[%22EQ%22]&from=__startDate__&to=__endDate__&csv=true";

    private static Integer totalRounds = 5;

    static{
        additionalHeaders.put("Referer",homePageUrl+"/");
        additionalHeaders.put("Connection","keep-alive");
        additionalHeaders.put("Upgrade-Insecure-Requests","1");
        additionalHeaders.put("Sec-Fetch-Dest","document");
        additionalHeaders.put("Sec-Fetch-Mode","navigate");
        additionalHeaders.put("Sec-Fetch-Site","same-origin");
        additionalHeaders.put("Sec-Fetch-User","?1");
        additionalHeaders.put("TE","trailers");

    }
    
    public void homePage(String ticker, CookieHandler cookies) throws IOException{
        Http.loadPage(homePageUrl, cookies);
    }

    public void tickerPage(String ticker, CookieHandler cookies) throws IOException{
        Http.loadPage(
            tickerPageUrl + ticker
            , cookies);
    }

    public HttpResponse tickerDetails(String ticker, CookieHandler cookies) throws IOException{
        return Http.loadPage(
            tickerDetailsPageUrl + ticker
            , cookies);
    }

    
    public HttpResponse tickerHistoricalPriceDetails(String ticker, CookieHandler cookies,String startDate, String endDate) throws IOException{
        return Http.loadPage(
            tickerHistoricalPriceDetailsUrl
                .replaceFirst("__ticker__", ticker)
                .replaceFirst("__startDate__", startDate)
                .replaceFirst("__endDate__", endDate)
            , cookies,additionalHeaders);
    }


    public List<LoadBulkTickers> getBulkTickerData(String ticker,String startDate, String endDate){
        try{
            CookieHandler cookies = new CookieHandler() ;
            homePage(ticker, cookies);
            tickerPage(ticker, cookies);
            return this.loadObjectList(tickerHistoricalPriceDetails(ticker, cookies,startDate,endDate).getResponseBody());
        }catch(Exception e){
            log.error(ticker,e.getMessage(), e);
            return null;
        }

    }



    public List<LoadBulkTickers> getAllBulkTickerData(Stock ticker){
        try{
            CookieHandler cookies = new CookieHandler() ;
            homePage(ticker.getTicker(), cookies);
            tickerPage(ticker.getTicker(), cookies);
            // return tickerHistoricalPriceDetails(ticker, startDate,endDate).getResponseBody();
            return null;
        }catch(Exception e){
            // log.error(ticker,e.getMessage(), e);
            return null;
        }

    }

    // @Scheduled(cron="#{${loader.dsp_ticker.scheduler.cron}}")
    public void runJob(){

        List<Stock> remainingTickers = stockService.getActiveTickers();
        Map<String, LoadBulkTickers> processedTickers = new HashMap<>();

        int rounds = 0;
        boolean allFailed = false;
        while( rounds < totalRounds && !allFailed && !remainingTickers.isEmpty()) {
            log.info( "DSP tickers loader: Round = {}, Remaining = {},  Retrieved = {}", rounds,remainingTickers.size(), processedTickers.keySet().size());
            AtomicInteger failureCount = new AtomicInteger();
            remainingTickers.stream().parallel().forEach(item -> {
                List<LoadBulkTickers> bulkTicker = getAllBulkTickerData(item);
                if (bulkTicker != null)
                    processedTickers.put(item.getTicker(), bulkTicker);
                else {
                    failureCount.getAndIncrement();
                }
            });

            remainingTickers.removeAll(processedTickers.keySet());
            log.info( "DSP tickers Currently Retrieved count = {}", processedTickers.keySet().size());
            rounds++;
        }
    }


    public List<LoadBulkTickers> loadObjectList(String dataString) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = CsvSchema.builder()
                .addColumn("Date")
                .addColumn("series")
                .addColumn("OPEN")
                .addColumn("HIGH")
                .addColumn("LOW")
                .addColumn("PREV. CLOSE")
                .addColumn("ltp")
                .addColumn("close")
                .addColumn("vwap")
                .addColumn("52W H")
                .addColumn("52W L")
                .addColumn("VOLUME")
                .addColumn("VALUE")
                .addColumn("No of trades")
                .build()
                .withHeader();
            MappingIterator<LoadBulkTickers> it = csvMapper.readerFor(LoadBulkTickers.class).with(schema).readValues(dataString);
            return it.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file {}", dataString, e);
            return Collections.emptyList();
        }
    }
}
