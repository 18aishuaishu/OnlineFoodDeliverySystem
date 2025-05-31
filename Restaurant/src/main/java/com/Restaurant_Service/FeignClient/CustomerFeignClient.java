package com.Restaurant_Service.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.Restaurant_Service.Entity.Customer;

@FeignClient(name = "customer", url = "http://localhost:2001/customers") 

public interface CustomerFeignClient {
	 @GetMapping("/getall")
	    List<Customer> getAllCustomers();

}
