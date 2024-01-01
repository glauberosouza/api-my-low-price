package com.glauber.MyLowPrice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MyLowPriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyLowPriceApplication.class, args);
	}

}