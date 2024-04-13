package com.asap.openrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OpenRunApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenRunApplication.class, args);
	}

}
