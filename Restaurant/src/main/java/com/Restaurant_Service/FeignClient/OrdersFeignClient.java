package com.Restaurant_Service.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Restaurant_Service.Entity.Order;

@FeignClient(name = "order", url = "http://localhost:2009/orders") 
public interface OrdersFeignClient {
	   // Get all orders
    @GetMapping("/getall")
    public List<Order> getAllOrders();
    // Update order status
    @PutMapping("/{id}/status")    
    Order updateOrderStatus(@PathVariable Long id, @RequestParam String status);
    @GetMapping("/restaurant/{restaurantId}/customer/{customerId}")
    ResponseEntity<List<Order>> getOrdersByCustomerAndRestaurant(
            @PathVariable("customerId") Long customerId,
            @PathVariable("restaurantId") Long restaurantId);
}
