package com.Admin_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Admin_Service.Entity.Delivery;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "Delivery", url = "http://localhost:2002/deliveries") // URL or Service Name for Delivery
																				// Service
public interface DeliveryServiceFeignClient {

	@GetMapping("/getall")
	List<Delivery> getAllDeliveries();

	@PutMapping("/{id}/status")
	Delivery updateDeliveryStatus(@PathVariable("id") Long id, @RequestParam("status") String status);

	@GetMapping("/getbyid/{id}")
	Optional<Delivery> getDeliveryById(@PathVariable("id") Long id);

	// Delete a delivery by ID
	@DeleteMapping("/{id}")
	void deleteDelivery(@PathVariable("id") Long id);

}
