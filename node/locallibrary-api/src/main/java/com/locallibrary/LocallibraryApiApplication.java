package com.locallibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.locallibrary.repository")
public class LocallibraryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocallibraryApiApplication.class, args);
	}
}
