package com.Customer_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Customer_Service.DTO.PaymentDTO;
import com.Customer_Service.Entity.Payment;


@FeignClient(name = "payment", url = "http://localhost:2007/payments")  // Assuming PaymentService is running at this URL
public interface PaymentClient {

    // POST request to process payment in PaymentService
    @PostMapping("/process")
    Payment processPayment(@RequestBody PaymentDTO paymentDTO);  // Sends PaymentDTO and gets Payment back
}

