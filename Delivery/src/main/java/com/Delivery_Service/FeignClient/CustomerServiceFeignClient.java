package com.Delivery_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Delivery_Service.Entity.Customer;
@FeignClient(name = "customer", url = "http://localhost:2001/customers") // Assuming the Customer service is running on localhost:8081

public interface CustomerServiceFeignClient {
    @GetMapping("/getbyid/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);
  
}