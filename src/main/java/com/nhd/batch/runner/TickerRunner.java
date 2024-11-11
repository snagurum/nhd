package com.nhd.batch.runner;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.nhd.batch.http.util.Constants;
import com.nhd.batch.http.util.Http;
import com.nhd.models.Ticker;

public class TickerRunner {
    
    private static final Logger log = LoggerFactory.getLogger(TickerRunner.class);

	public String getTickers() throws IOException {
        return Http.loadPage(Constants.NSE_TICKERS_URL, null, true).getResponseBody();
	}

    public List<Ticker>  run(){
        try{
            return this.loadObjectList(getTickers());
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public List<Ticker> loadObjectList(String dataString) {
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
            MappingIterator<Ticker> it = csvMapper.readerFor(Ticker.class).with(schema).readValues(dataString);
            List<Ticker> tickers = it.readAll();
            return tickers;
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + dataString, e);
            return Collections.emptyList();
        }
    }


}
