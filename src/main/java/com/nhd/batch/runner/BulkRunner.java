package com.nhd.batch.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    
    public void homePage(Stock ticker, CookieHandler cookies) throws IOException{
        log.debug("homePage {}",ticker.getTicker());
        Http.loadPage(homePageUrl, cookies);
    }

    public void tickerPage(Stock ticker, CookieHandler cookies) throws IOException{
        log.debug("tickerPage {}",ticker.getTicker());
        Http.loadPage(
            tickerPageUrl + ticker
            , cookies);
    }

    public HttpResponse tickerDetails(Stock ticker, CookieHandler cookies) throws IOException{
        log.debug("tickerDetails {}",ticker.getTicker());
        return Http.loadPage(
            tickerDetailsPageUrl + ticker
            , cookies);
    }

    
    public HttpResponse tickerHistoricalPriceDetails(Stock ticker, CookieHandler cookies,String startDate, String endDate) throws IOException{
        log.debug("tickerHistoricalPriceDetails {}",ticker.getTicker());
        return Http.loadPage(
            tickerHistoricalPriceDetailsUrl
                .replaceFirst("__ticker__", ticker.getTicker())
                .replaceFirst("__startDate__", startDate)
                .replaceFirst("__endDate__", endDate)
            , cookies,additionalHeaders);
    }


    public List<LoadBulkTickers> getYearlyBulkTickerData(Stock ticker,String startDate, String endDate){
        try{
            CookieHandler cookies = new CookieHandler() ;
            homePage(ticker, cookies);
            tickerPage(ticker, cookies);
            return this.loadObjectList(tickerHistoricalPriceDetails(ticker, cookies,startDate,endDate).getResponseBody());
        }catch(Exception e){
            log.error(ticker.getTicker(),e.getMessage(), e);
            return null;
        }

    }


    public List<String> getDates(Stock ticker){
        List<String> availableDates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ticker.getDateOfListing());
        int startYear = calendar.get(Calendar.YEAR);
        Date endDate = new Date();
        calendar.setTime(endDate);
        int endYear = calendar.get(Calendar.YEAR);

        for(int i=startYear;i<=endYear;i++){
            availableDates.add("01-01-"+i+"__31-12-"+i);
        }
        return availableDates;
    }

    public List<LoadBulkTickers> getAllBulkTickerData(Stock ticker){
        try{
            List<String> datesList = this.getDates(ticker);
            List<LoadBulkTickers> loadBulkTickersList = new ArrayList<>();
            datesList.forEach(e ->{
                loadBulkTickersList.addAll(getYearlyBulkTickerData(ticker, e.split("__")[0],e.split("__")[1]));
            });
            return loadBulkTickersList;
        }catch(Exception e){
            return null;
        }

    }

    @Scheduled(cron="#{${loader.bulk_ticker.scheduler.cron}}")
    public void runJob(){

        List<Stock> temp = stockService.noHistoryStocks();
        if(temp == null || temp.isEmpty()) {
            log.info("No ticker left for Bulk load");
            return;
        }
        List<Stock> remainingTickers = new ArrayList<>();
        remainingTickers.add(temp.get(0)); temp.remove(0);
        remainingTickers.add(temp.get(1)); temp.remove(0);
        remainingTickers.add(temp.get(2)); temp.remove(0);
        remainingTickers.add(temp.get(3)); temp.remove(0);
        remainingTickers.add(temp.get(4)); temp.remove(0);
        Map<String, List<LoadBulkTickers>> processedTickers = new HashMap<>();

        int rounds = 0;
        boolean allFailed = false;
        JobStatus audit = stockService.startJobWithComment(JobName.BSP_TICKER,remainingTickers.stream().map(Stock::getTicker).collect(Collectors.joining()));
        while( rounds < totalRounds && !allFailed && !remainingTickers.isEmpty()) {
            log.info( "DSP tickers loader: Round = {}, Remaining = {},  Retrieved = {}", rounds,remainingTickers.size(), processedTickers.keySet().size());
            AtomicInteger failureCount = new AtomicInteger();
            remainingTickers.stream().forEach(item -> {
                log.info("processing LoadBulkTickers for {}, remaining Count = {}, processed Count = {} ", item.getTicker(),remainingTickers.size(),processedTickers.keySet().size());
                List<LoadBulkTickers> bulkTickerList = getAllBulkTickerData(item);
                bulkTickerList.forEach(i -> i.setTicker(item.getTicker()));
                if (bulkTickerList != null && !bulkTickerList.isEmpty()) {
                    item.setHistoryLoaded(true);
                    stockService.saveAllLoadBulkTickers(bulkTickerList);
                    stockService.saveStock(item);
                    processedTickers.put(item.getTicker(), bulkTickerList);
                }else {
                    failureCount.getAndIncrement();
                    log.info( "DSP tickers Currently Processed count = {}, failureCount = {}", processedTickers.keySet().size(),failureCount);
                }
            });
            remainingTickers.removeAll(processedTickers.keySet());
            rounds++;
        }
        audit.setSuccessCount(processedTickers.keySet().size());
        stockService.endJob(audit);
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
