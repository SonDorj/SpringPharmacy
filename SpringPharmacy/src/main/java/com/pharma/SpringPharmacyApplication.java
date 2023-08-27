package com.pharma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringPharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPharmacyApplication.class, args);
	}

}