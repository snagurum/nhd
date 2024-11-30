package com.nhd.service.repo;

import com.nhd.models.LoadDspTickers;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadDspTickersRepository extends CrudRepository<LoadDspTickers, Long>{

    }
