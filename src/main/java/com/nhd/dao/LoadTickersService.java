package com.nhd.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhd.models.LoadTickers;


public interface LoadTickersService{
        void saveAll(List<LoadTickers> tickers);
}