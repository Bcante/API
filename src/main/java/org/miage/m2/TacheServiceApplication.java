package org.miage.m2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TacheServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacheServiceApplication.class, args);
	}
}
