package com.nhd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhd.batch.runner.DspRunner;
import com.nhd.models.LoadDspTickers;

@RestController
public class HelloController {


	@Autowired
	DspRunner dspTickerRunner;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/dsp")
	public LoadDspTickers dailyStockPrice(@RequestParam String ticker) {
		return dspTickerRunner.getDspTickerInfo((ticker));
	}

}