package com.nhd.service;

import java.util.List;

import com.nhd.models.LoadDspTickers;
import com.nhd.service.repo.LoadDspTickersRepository;
import com.nhd.service.repo.LoadTickersRepository;
import com.nhd.service.repo.StockRepository;
import com.nhd.models.LoadTickers;
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
    StockRepository stockRepo;


    public List<String> getActiveTickers(){
        return stockRepo.getActiveTickers();
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



}
