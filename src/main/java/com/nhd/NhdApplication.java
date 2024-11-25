package com.nhd;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nhd.batch.runner.TickerRunner;

@SpringBootApplication
public class NhdApplication {

	@Autowired
	TickerRunner tickerRunner;

	public static void main(String[] args) {
		SpringApplication.run(NhdApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			tickerRunner.run();
		};
	}


}
