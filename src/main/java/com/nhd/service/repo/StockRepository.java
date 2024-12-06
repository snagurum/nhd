package com.nhd.service.repo;

import com.nhd.models.Stock;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    @Query("select ticker from lt.stocks s where s.active is true")
    List<String> getActiveTickers();

}
