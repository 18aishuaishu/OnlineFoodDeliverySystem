package com.Admin_Service.FeignClient;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.Admin_Service.DTO.PaymentDTO;
import com.Admin_Service.Entity.Payment;

@FeignClient(name = "payment", url = "http://localhost:2007/payments") // URL of the Payment Service
public interface PaymentServiceFeignClient {
	// Get all payments

	@GetMapping("/getall")
	List<Payment> getAllPayments();

	@PostMapping("/process")
	Payment processPayment(@RequestBody PaymentDTO paymentDTO); // Sends PaymentDTO and gets Payment back

	// Get payment by ID
	@GetMapping("/getbyid/{id}")
	Payment getById(@PathVariable Long id);

	// Get payment by Order ID
	@GetMapping("/{orderId}")
	Payment getPaymentByOrderId(@PathVariable Long orderId);

	// Update payment status
	@PutMapping("/{id}/status")
	Payment updatePaymentStatus(@PathVariable Long id, @RequestParam String status);
}
