package com.triple.pointApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PointApiApplication {

	public static void main(String[] args) {

		Hello hello = new Hello();

		hello.setData("pointAPI");
		String data = hello.getData();
		System.out.println("data = " + data);

		SpringApplication.run(PointApiApplication.class, args);



	}

}
