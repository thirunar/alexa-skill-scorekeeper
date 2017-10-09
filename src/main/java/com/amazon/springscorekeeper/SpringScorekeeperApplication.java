package com.amazon.springscorekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.amazon.springscorekeeper", "com.alexaframework.springalexa"})
public class SpringScorekeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringScorekeeperApplication.class, args);
	}
}
