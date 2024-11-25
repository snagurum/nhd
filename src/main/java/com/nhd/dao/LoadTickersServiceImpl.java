package com.nhd.dao;

import java.util.List;

import com.nhd.models.LoadTickers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadTickersServiceImpl implements LoadTickersService {
    
    @Autowired
    LoadTickersRepository repo;


    public void saveAll(List<LoadTickers> tickers){

            System.out.println(" truncating table");
            repo.truncateTable();
            System.out.println(" save tickers");
            repo.saveAll(tickers);
            System.out.println(" mark new tickers");
            repo.markNewTickers();
            System.out.println(" add new tickers");
            repo.addNewStocks();
        }

}
