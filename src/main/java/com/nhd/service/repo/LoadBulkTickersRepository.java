package com.nhd.service.repo;

import com.nhd.models.LoadBulkTickers;
import com.nhd.models.LoadDspTickers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoadBulkTickersRepository extends CrudRepository<LoadBulkTickers, Long> {


}

