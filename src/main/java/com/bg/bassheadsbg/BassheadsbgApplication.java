package com.bg.bassheadsbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BassheadsbgApplication {

	public static void main(String[] args) {
		SpringApplication.run(BassheadsbgApplication.class, args);
	}

}
