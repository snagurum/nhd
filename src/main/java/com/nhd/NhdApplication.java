package com.nhd;

import java.util.Arrays;

import com.nhd.batch.runner.DspRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nhd.batch.runner.TickerRunner;

@SpringBootApplication
public class NhdApplication {
	private static final Logger log = LoggerFactory.getLogger(NhdApplication.class);

	@Autowired
	TickerRunner tickerRunner;

	@Autowired
	DspRunner dspTickerRunner;


	public static void main(String[] args) {
		SpringApplication.run(NhdApplication.class, args);
	}

	@Bean
	@Order(1)
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			tickerRunner.run();
		};
	}


	@Bean
	@Order(2)
	public CommandLineRunner commandLineRunner1(ApplicationContext ctx) {
		return args -> {
			dspTickerRunner.run();
		};
	}

}
