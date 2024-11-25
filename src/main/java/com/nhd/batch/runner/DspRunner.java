package com.nhd.batch.runner;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nhd.models.DspTicker;
import com.nhd.batch.http.util.Constants;
import com.nhd.batch.http.util.CookieHandler;
import com.nhd.batch.http.util.Http;
import com.nhd.models.HttpResponse;

@Service
public class DspRunner {

    private static final Logger log = LoggerFactory.getLogger(DspRunner.class);

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
            Constants.NSE_HOME_URL+"/api/quote-equity?symbol=" + "&section=trade_info" 
            , cookies, true);
    }

    public DspTicker run(String ticker){
        try{
            CookieHandler cookies = new CookieHandler() ;
            homePage(ticker, cookies);
            tickerPage(ticker, cookies);
            String companyDetails = tickerDetails(ticker, cookies).getResponseBody();
            String tradeDetails = tickerTradeDetails(ticker, cookies).getResponseBody();
            return new DspTicker(ticker, companyDetails, tradeDetails);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new DspTicker(ticker);
        }
    }
}
