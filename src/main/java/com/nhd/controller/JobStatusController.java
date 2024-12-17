package com.nhd.controller;


import com.nhd.batch.runner.BulkRunner;
import com.nhd.batch.runner.DspRunner;
import com.nhd.batch.runner.TickerRunner;
import com.nhd.models.JobStatus;
import com.nhd.models.LoadBulkTickers;
import com.nhd.models.LoadDspTickers;
import com.nhd.models.Stock;
import com.nhd.service.AuditService;
import com.nhd.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class JobStatusController {

	@Autowired
	DspRunner dspTickerRunner;

	@Autowired
	BulkRunner bulkTickerRunner;

	@Autowired
	TickerRunner tickerRunner;

	@Autowired
	AuditService auditService;

	@GetMapping("/tjs")
	public List<JobStatus> index() {
		return auditService.getTodaysJobStatus();
	}

	@GetMapping("/help")
	public String help(){
		StringBuilder message = new StringBuilder();
		message
				.append("/help             help message" ).append("\n")
  			   	.append("/tjs              Today's Job Status ").append("\n")
			   	.append("/runJob/ticker    Run Ticker job").append("\n")
				.append("/runJob/dspTicker Run DSP Ticker job").append("\n");
		return message.toString();
	}

	@GetMapping("/runJob/ticker")
	public void runTickerJob(){
		tickerRunner.runJob();
	}

	@GetMapping("/runJob/dspTicker")
	public void runDspTickerJob(){
		dspTickerRunner.runJob();
	}

	@GetMapping("/runJob/bulkTicker")
	public void runBulkTickerJob(){
		bulkTickerRunner.runJob();
	}

	@GetMapping("/test/dsp")
	public LoadDspTickers dspTest() {
		Stock stock = new Stock();
		stock.setTicker("INFY");
		return dspTickerRunner.getDspTickerInfo(stock);
	}

	@GetMapping("/test/bulk")
	public List<LoadBulkTickers> bulkTest(@RequestParam Optional<String> ticker, @RequestParam Optional<String> dol) {
		Stock stock = new Stock();
		if(!ticker.isPresent()){
			 stock.setTicker("INFY");
			 stock.setDateOfListing(Date.valueOf("1995-08-08"));
			// stock.setTicker("ZOMATO");
			// stock.setDateOfListing(Date.valueOf("2024-01-09"));
//			stock.setTicker("TATATECH");
//			stock.setDateOfListing(Date.valueOf("2023-01-09"));
		}
		else{ 
			stock.setTicker( ticker.get());
			stock.setDateOfListing(Date.valueOf(dol.get()));
		}
		return bulkTickerRunner.getAllBulkTickerData(stock);
	}

}