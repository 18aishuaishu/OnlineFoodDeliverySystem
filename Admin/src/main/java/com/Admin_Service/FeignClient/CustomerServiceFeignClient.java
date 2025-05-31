package com.Admin_Service.FeignClient;

import com.Admin_Service.Entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customer", url = "http://localhost:2001/customers") // URL or Service Name for Customer
																						// Service
public interface CustomerServiceFeignClient {
	@GetMapping("/getall")
	List<Customer> getAllCustomers();

	@GetMapping("/getbyid/{id}")
	Customer getCustomerById(@PathVariable Long id);

	@PostMapping
	Customer createCustomer(@RequestBody Customer customer);

	@PutMapping("/{customerId}")
	Customer updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer);

	@DeleteMapping("/delete/{id}")
	void deleteCustomer(@PathVariable Long id);
}
