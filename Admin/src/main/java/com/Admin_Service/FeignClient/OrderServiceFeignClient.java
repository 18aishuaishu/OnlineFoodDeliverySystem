package com.Admin_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Admin_Service.DTO.CreateOrderRequest;
import com.Admin_Service.Entity.Order;

import java.util.List;

@FeignClient(name = "Order", url = "http://localhost:2009/orders") // URL or Service Name for Order Service
public interface OrderServiceFeignClient {
	// Get order by ID
	

	// Get all orders
	@GetMapping("/getall")
	List<Order> getAllOrders();

	 @PostMapping("/post")
	    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest);
	// Update the order status
	@PutMapping("/{id}/status")
	Order updateOrderStatus(@PathVariable Long id, @RequestParam String status);

	// Delete an order
	@DeleteMapping("/{id}")
	void deleteOrder(@PathVariable Long id);
	

	    @GetMapping("/getbyid/{id}")
	    Order getOrderById(@PathVariable Long id);


}