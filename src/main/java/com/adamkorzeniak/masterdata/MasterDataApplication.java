package com.adamkorzeniak.masterdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class MasterDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterDataApplication.class, args);
	}
}
