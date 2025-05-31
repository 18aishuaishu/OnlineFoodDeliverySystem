package com.Restaurant_Service.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Restaurant_Service.Entity.Delivery;

@FeignClient(name = "delivery", url = "http://localhost:2002/deliveries") 

public interface DeliveryFeignClient {
	@GetMapping("/getall")
    List<Delivery> getAllDeliveries();

    @PutMapping("/{id}/status")
    Delivery updateDeliveryStatus(@PathVariable("id") Long id, @RequestParam("status") String status);

}
