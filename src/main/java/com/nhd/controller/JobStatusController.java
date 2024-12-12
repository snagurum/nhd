package com.nhd.controller;


import com.nhd.batch.runner.BulkRunner;
import com.nhd.batch.runner.DspRunner;
import com.nhd.batch.runner.TickerRunner;
import com.nhd.models.JobStatus;
import com.nhd.models.LoadBulkTickers;
import com.nhd.models.LoadDspTickers;
import com.nhd.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobStatusController {

	@Autowired
	DspRunner dspTickerRunner;

	@Autowired
	BulkRunner bulkTickerRunner;

	@Autowired
	TickerRunner tickerRunner;

	@Autowired
	StockService stockService;

	@GetMapping("/tjs")
	public List<JobStatus> index() {
		return stockService.getTodaysJobStatus();
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

	@GetMapping("/test/dsp")
	public LoadDspTickers dspTest() {
		return dspTickerRunner.getDspTickerInfo("INFY");
	}

	@GetMapping("/test/bulk")
	public String bulkTest() {
		return bulkTickerRunner.getBulkTickerData("INFY","01-01-2023","01-12-2023");
	}

}