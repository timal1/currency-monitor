package com.ladatko.currencymonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyMonitorApplication.class, args);
	}

}
