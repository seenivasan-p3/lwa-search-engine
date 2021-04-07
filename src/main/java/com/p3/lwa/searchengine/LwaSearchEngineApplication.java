package com.p3.lwa.searchengine;

import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableIgniteRepositories
@EnableAsync
@EnableScheduling
public class LwaSearchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LwaSearchEngineApplication.class, args);
	}


}
