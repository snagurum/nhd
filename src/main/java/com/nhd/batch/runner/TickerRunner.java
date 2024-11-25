package com.nhd.batch.runner;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.nhd.batch.http.util.Constants;
import com.nhd.batch.http.util.Http;
import com.nhd.dao.LoadTickersService;
import com.nhd.models.LoadTickers;
import org.springframework.stereotype.Component;

@Component
public class TickerRunner {
    
    private static final Logger log = LoggerFactory.getLogger(TickerRunner.class);

    @Autowired
    private LoadTickersService loadTickersService ; 

	public String getTickers() throws IOException {
        return Http.loadPage(Constants.NSE_TICKERS_URL, null, true).getResponseBody();
	}

    public List<LoadTickers>  run(){
        try{
            List<LoadTickers> tickers = this.loadObjectList(getTickers());
            loadTickersService.saveAll(tickers );
            System.out.println(" count  = " + tickers.size() );
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public List<LoadTickers> loadObjectList(String dataString) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = CsvSchema.builder()
                .addColumn("SYMBOL")
                .addColumn("NAME OF COMPANY")
                .addColumn("SERIES")
                .addColumn("DATE OF LISTING")
                .addColumn("PAID UP VALUE")
                .addColumn("MARKET LOT")
                .addColumn("ISIN NUMBER")
                .addColumn("FACE VALUE")
                .build()
                .withHeader();
            MappingIterator<LoadTickers> it = csvMapper.readerFor(LoadTickers.class).with(schema).readValues(dataString);
            List<LoadTickers> tickers = it.readAll();
            return tickers;
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + dataString, e);
            return Collections.emptyList();
        }
    }


}
