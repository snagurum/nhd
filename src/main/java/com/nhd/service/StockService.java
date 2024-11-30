package com.nhd.service;

import java.util.List;

import com.nhd.models.LoadDspTickers;
import com.nhd.models.LoadTickers;


public interface StockService{

        List<String> getActiveTickers();

        void saveAllLoadTickers(List<LoadTickers> tickers);

        void saveAllLoadDspTickers(List<LoadDspTickers> tickers);

}