package com.triple.tripleMileageService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TripleMileageServiceApplication {

	public static void main(String[] args) {

		Hello hello = new Hello();
		hello.setData("tripleMileageService");
		System.out.println("hello.getData() = " + hello.getData());


		SpringApplication.run(TripleMileageServiceApplication.class, args);
	}

}
