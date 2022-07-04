package com.triple.triplePointApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TriplePointApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriplePointApiApplication.class, args);
	}

}
