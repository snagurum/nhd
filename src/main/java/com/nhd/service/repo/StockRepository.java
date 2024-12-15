package com.nhd.service.repo;

import com.nhd.models.LoadTickers;
import com.nhd.models.Stock;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    @Query("select s.* from lt.stocks s where s.active is true")
    List<Stock> getActiveTickers();

    @Query("select s.* from lt.stocks s where s.history_loaded is false")
    List<Stock> noHistoryStocks();
}
