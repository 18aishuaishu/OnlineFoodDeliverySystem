package com.Customer_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "com.Customer_Service.Entity")  // Ensure this matches your package
//@EnableJpaRepositories(basePackages = "com.Customer_Service.Reposiory")
@EnableFeignClients
public class CustomerServiceApplication {
//	@Bean
//	public RestTemplate resttemplate() {
//		return new RestTemplate();
//	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
