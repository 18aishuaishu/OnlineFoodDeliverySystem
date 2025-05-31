package com.Restaurant_Service.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Restaurant_Service.Entity.Payment;

@FeignClient(name = "payment", url = "http://localhost:2007/payments") // URL of the Payment Service
public interface PaymentFeignClient {

	@GetMapping("/getall")
    List<Payment> getAllPayments();



    // Get payment by payment ID
    @GetMapping("/getbyid/{id}")
    Payment getById(@PathVariable Long id);
}