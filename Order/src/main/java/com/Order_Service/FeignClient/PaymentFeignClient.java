package com.Order_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.Order_Service.Entity.Payment;
@FeignClient(name = "payment", url = "http://localhost:2007/payments") // URL of the Payment Service

public interface PaymentFeignClient {

	 @PostMapping("/create")
	    Payment createPayment(Payment payment);

}
